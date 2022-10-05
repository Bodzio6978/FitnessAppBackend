package com.gmail.bogumilmecel2.user.log.routes

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.domain.model.LogRequest
import com.gmail.bogumilmecel2.user.log.domain.use_case.InsertLogEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configurePostLogEntryRoute(
    insertLogEntry: InsertLogEntry
) {
    authenticate {
        post("") {
            val logRequest = call.receiveOrNull<LogRequest>()
            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)?.toIntOrNull()
            principalId?.let { userId ->
                logRequest?.let {
                    val resource = insertLogEntry(
                        timestamp = logRequest.timestamp,
                        userId = userId
                    )

                    when (resource) {
                        is Resource.Error -> {
                            call.respond(
                                HttpStatusCode.Conflict
                            )
                            return@post
                        }

                        is Resource.Success -> {
                            resource.data?.let { data ->
                                call.respond(
                                    HttpStatusCode.OK,
                                    message = data
                                )
                            } ?: kotlin.run {
                                call.respond(
                                    HttpStatusCode.Conflict
                                )
                                return@post
                            }
                        }
                    }
                } ?: kotlin.run {
                    call.respond(
                        HttpStatusCode.BadRequest
                    )
                    return@post
                }
            } ?: kotlin.run {
                call.respond(
                    HttpStatusCode.Unauthorized
                )
                return@post
            }
        }
    }
}