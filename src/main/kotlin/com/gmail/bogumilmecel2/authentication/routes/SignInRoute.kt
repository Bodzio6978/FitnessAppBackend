package com.gmail.bogumilmecel2.authentication.routes

import com.gmail.bogumilmecel2.authentication.domain.model.AuthRequest
import com.gmail.bogumilmecel2.authentication.domain.model.AuthResponse
import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenConfig
import com.gmail.bogumilmecel2.authentication.domain.use_case.GetUserByUsername
import com.gmail.bogumilmecel2.common.util.Resource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureSignInRoute(
    getUserByUsername: GetUserByUsername,
    tokenConfig: TokenConfig
){
    post("/signin/") {
        val request = call.receiveOrNull<AuthRequest>()
        request?.let {
            val resource = getUserByUsername(
                email = request.email,
                password = request.password,
                tokenConfig = tokenConfig
            )
            println(resource.toString())

            when(resource){
                is Resource.Error -> {
                    println(resource.error)
                    call.respond(
                        HttpStatusCode.Conflict
                    )
                    return@post
                }
                is Resource.Success -> {
                    resource.data?.let {
                        call.respond(
                            HttpStatusCode.OK,
                            message = AuthResponse(
                                token = it
                            )
                        )
                    } ?: kotlin.run {
                        call.respond(
                            HttpStatusCode.Conflict
                        )
                        return@post
                    }

                }
            }
        } ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
    }
}