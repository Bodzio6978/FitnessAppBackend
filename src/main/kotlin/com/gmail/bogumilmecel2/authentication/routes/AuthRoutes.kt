package com.gmail.bogumilmecel2.authentication.routes

import com.gmail.bogumilmecel2.authentication.domain.model.AuthRequest
import com.gmail.bogumilmecel2.authentication.domain.model.AuthResponse
import com.gmail.bogumilmecel2.authentication.domain.model.user.User
import com.gmail.bogumilmecel2.authentication.domain.repository.AuthenticationRepository
import com.gmail.bogumilmecel2.security.domain.model.hash.SaltedHash
import com.gmail.bogumilmecel2.security.domain.model.token.TokenClaim
import com.gmail.bogumilmecel2.security.domain.model.token.TokenConfig
import com.gmail.bogumilmecel2.security.domain.service.HashingService
import com.gmail.bogumilmecel2.security.domain.service.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticationRoute(
    hashingService: HashingService,
    authenticationRepository: AuthenticationRepository,
    tokenService: TokenService,
    tokenConfig: TokenConfig
){
    route("authentication"){
        post("signup") {
            val request = call.receiveOrNull<AuthRequest>()
            request?.let {

                val saltedHash = hashingService.generateSaltedHash(request.password)
                val user = User(
                    username = request.username,
                    password = saltedHash.hash,
                    salt = saltedHash.salt
                )

                val wasUserAdded = authenticationRepository.registerNewUser(user)
                if (!wasUserAdded){
                    call.respond(HttpStatusCode.Conflict)
                    return@post
                }

                call.respond(HttpStatusCode.OK)

            } ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }

        post("signin") {
            val request = call.receiveOrNull<AuthRequest>()
            request?.let {
                val user = authenticationRepository.getUserByUsername(request.username)

                if (user == null){
                    call.respond(HttpStatusCode.Conflict)
                    return@post
                }

                val isValidPassword = hashingService.verify(
                    value = request.password,
                    saltedHash = SaltedHash(
                        hash = user.password,
                        salt = user.salt
                    )
                )

                if(!isValidPassword){
                    call.respond(HttpStatusCode.Conflict)
                    return@post
                }

                val token = tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = user.id.toString()
                    )
                )

                call.respond(
                    HttpStatusCode.OK,
                message = AuthResponse(
                    token = token
                ))

            } ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }

        authenticate {
            get("authenticate") {
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}

