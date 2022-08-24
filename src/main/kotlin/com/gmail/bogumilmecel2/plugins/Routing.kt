package com.gmail.bogumilmecel2.plugins

import com.gmail.bogumilmecel2.routes.registerCustomerRoutes
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.configureRouting() {
//    install(StatusPages) {
//        exception<AuthenticationException> { call, cause ->
//            call.respond(HttpStatusCode.Unauthorized)
//        }
//        exception<AuthorizationException> { call, cause ->
//            call.respond(HttpStatusCode.Forbidden)
//        }
//
//    }

    registerCustomerRoutes()
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
