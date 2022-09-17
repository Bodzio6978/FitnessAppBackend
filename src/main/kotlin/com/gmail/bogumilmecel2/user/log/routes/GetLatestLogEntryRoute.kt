package com.gmail.bogumilmecel2.user.log.routes

import com.gmail.bogumilmecel2.common.exception.NoDatabaseEntryException
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
){
    authenticate {
        get {
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)?.toIntOrNull()
                principalId?.let { userId ->
                    val resource = getLatestLogEntry(
                        userId = userId
                    )
                    when (resource) {
                        is Resource.Error -> {
                            resource.error?.let {
                                when (it) {
                                    is NoDatabaseEntryException -> {
                                        call.respond(
                                            HttpStatusCode.NotFound,
                                            message = false
                                        )
                                        return@get
                                    }

                                    else -> {
                                        call.respond(HttpStatusCode.Conflict)
                                        return@get
                                    }
                                }
                            }
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
                                    HttpStatusCode.Conflict
                                )
                                return@get
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