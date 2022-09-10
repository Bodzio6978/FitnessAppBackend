package com.gmail.bogumilmecel2.authentication.routes

import com.gmail.bogumilmecel2.authentication.domain.use_case.AuthRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authenticationRoute(
    authRoutes: AuthRoutes
){
    route("authentication"){

        configureSignUpRoute(authRoutes.registerNewUser)

        authenticate {
            get("/authenticate") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.getClaim("userId", String::class)
                println(userId)
                call.respond(
                    HttpStatusCode.OK,
                    message = true
                )
            }
        }
    }
}

