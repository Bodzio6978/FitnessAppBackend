package com.gmail.bogumilmecel2.authentication.routes

import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenConfig
import com.gmail.bogumilmecel2.authentication.domain.use_case.AuthRoutes
import io.ktor.server.routing.*

fun Route.configureAuthRoutes(
    authRoutes: AuthRoutes,
    tokenConfig: TokenConfig
){
    route("authentication"){
        configureSignUpRoute(authRoutes.registerNewUser)
        configureSignInRoute(
            getUserByUsername = authRoutes.getUserByUsername,
            tokenConfig = tokenConfig
        )
        configureAuthenticateRoute()
    }
}

