package com.gmail.bogumilmecel2.authentication.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureAuthenticateRoute(){
    authenticate {
        get("/authenticate") {
            call.respond(
                HttpStatusCode.OK,
                message = true
            )
        }
    }
}