package com.gmail.bogumilmecel2.common.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gmail.bogumilmecel2.authentication.domain.repository.AuthenticationRepository
import com.gmail.bogumilmecel2.authentication.routes.authenticationRoute
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.gmail.bogumilmecel2.security.data.service.JwtTokenService
import com.gmail.bogumilmecel2.security.data.service.SHA256HashingService
import com.gmail.bogumilmecel2.security.domain.model.token.TokenConfig
import io.ktor.server.routing.*

fun Application.configureAuthentication(
    authenticationRepository: AuthenticationRepository
){

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("ktor.jwt.issuer").getString(),
        audience = environment.config.property("ktor.jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    val hashingService = SHA256HashingService()

    install(Authentication){
        jwt {
            val environment = this@configureAuthentication.environment
            realm = environment.config.property("ktor.jwt.realm").getString()
            verifier {
                JWT
                    .require(Algorithm.HMAC256(tokenConfig.secret))
                    .withAudience(tokenConfig.audience)
                    .withIssuer(tokenConfig.issuer)
                    .build()
            }
            validate {credential ->
                if (credential.payload.audience.contains(tokenConfig.audience)){
                    JWTPrincipal(credential.payload)
                }else{
                    null
                }
            }

        }
    }

    routing {
        authenticationRoute(
            tokenConfig = tokenConfig,
            tokenService = tokenService,
            hashingService = hashingService,
            authenticationRepository = authenticationRepository
        )
    }
}