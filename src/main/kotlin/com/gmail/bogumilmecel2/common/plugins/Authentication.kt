package com.gmail.bogumilmecel2.common.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.gmail.bogumilmecel2.authentication.domain.repository.AuthenticationRepository
import com.gmail.bogumilmecel2.authentication.routes.authenticationRoute
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.gmail.bogumilmecel2.authentication.data.service.JwtTokenService
import com.gmail.bogumilmecel2.authentication.data.service.SHA256HashingService
import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenConfig
import io.ktor.server.routing.*

fun Application.configureAuthentication(
    authenticationRepository: AuthenticationRepository,
    tokenConfig: TokenConfig
){


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
}