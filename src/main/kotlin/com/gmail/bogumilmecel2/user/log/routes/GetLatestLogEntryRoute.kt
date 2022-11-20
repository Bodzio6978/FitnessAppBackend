package com.gmail.bogumilmecel2.user.log.routes

import com.gmail.bogumilmecel2.user.log.domain.model.LogRequest
import com.gmail.bogumilmecel2.user.log.domain.use_case.CheckLatestLogEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGetLatestLogEntryRoute(
    checkLatestLogEntry: CheckLatestLogEntry
) {
    authenticate {
        post("/latest/") {
            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)
            principalId?.let { userId ->
                val logRequest = call.receiveOrNull<LogRequest>()
                logRequest?.let { request ->
                    val logEntry = checkLatestLogEntry(userId = userId, timestamp = request.timestamp)
                    call.respond(
                        HttpStatusCode.OK,
                        message = logEntry
                    )
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