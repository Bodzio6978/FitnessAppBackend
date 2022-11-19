package com.gmail.bogumilmecel2.user.user_data.routes

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.user_data.domain.use_cases.GetUserInformation
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGetUserInformationRoute(
    getUserInformation: GetUserInformation
){
    authenticate {
        get("/userInformation/") {
            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)

            principalId?.let { userId ->
                val resource = getUserInformation(
                    userId = userId
                )

                when(resource){
                    is Resource.Error -> {
                        call.respond(
                            HttpStatusCode.Conflict
                        )
                        return@get
                    }
                    is Resource.Success -> {
                        resource.data?.let { data ->
                            call.respond(
                                HttpStatusCode.OK,
                                message = data
                            )
                        } ?: kotlin.run {
                            call.respond(
                                HttpStatusCode.OK
                            )
                            return@get
                        }
                    }
                }
            }?: kotlin.run {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }
        }
    }
}