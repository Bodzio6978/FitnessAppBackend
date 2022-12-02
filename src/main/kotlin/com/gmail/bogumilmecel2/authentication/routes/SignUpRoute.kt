package com.gmail.bogumilmecel2.authentication.routes

import com.gmail.bogumilmecel2.authentication.domain.model.AuthRequest
import com.gmail.bogumilmecel2.authentication.domain.use_case.RegisterNewUser
import com.gmail.bogumilmecel2.common.util.Resource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureSignUpRoute(
    registerNewUser: RegisterNewUser
){
    post("/signup/") {
        val request = call.receiveOrNull<AuthRequest>()
        request?.let {
            if (request.username == null){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            } else {
                val resource = registerNewUser(
                    email = request.email,
                    password = request.password,
                    username = request.username
                )

                when(resource){
                    is Resource.Success -> {
                        call.respond(
                            status = HttpStatusCode.OK,
                            message = true
                        )
                    }
                    is Resource.Error -> {
                        call.respond(HttpStatusCode.Conflict,)
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