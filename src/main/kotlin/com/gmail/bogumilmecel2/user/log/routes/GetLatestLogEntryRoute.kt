package com.gmail.bogumilmecel2.user.log.routes

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.domain.use_case.GetLatestLogEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGetLatestLogEntryRoute(
    getLatestLogEntry: GetLatestLogEntry
) {
    authenticate {
        get("/latest") {
            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)?.toIntOrNull()
            println(principalId.toString())
            principalId?.let { userId ->
                val resource = getLatestLogEntry(
                    userId = userId
                )
                when (resource) {
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
                                HttpStatusCode.NotFound
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                call.respond(
                    HttpStatusCode.Unauthorized
                )
                return@get
            }
        }
    }
}