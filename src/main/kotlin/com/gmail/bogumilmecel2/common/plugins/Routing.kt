package com.gmail.bogumilmecel2.common.plugins

import com.gmail.bogumilmecel2.diary_feature.routes.registerProductRoutes
import io.ktor.server.locations.*
import io.ktor.server.application.*

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

    registerProductRoutes()
}
class AuthenticationException : RuntimeException()
class AuthorizationException : RuntimeException()
