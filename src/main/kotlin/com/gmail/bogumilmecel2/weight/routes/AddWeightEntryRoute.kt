package com.gmail.bogumilmecel2.weight.routes

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.weight.domain.use_case.AddWeightEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureAddWeightEntryRoute(addWeightEntry: AddWeightEntry){
    authenticate {
        post("") {
            val weightEntry = call.receiveOrNull<WeightEntry>()
            weightEntry?.let { entry ->
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)
                principalId?.let { userId ->
                    val resource = addWeightEntry(
                        weightEntry = entry,
                        userId = userId
                    )

                    when(resource){
                        is Resource.Error -> {
                            call.respond(
                                HttpStatusCode.Conflict
                            )
                        }
                        is Resource.Success -> {
                            resource.data?.let { newEntry ->
                                call.respond(
                                    HttpStatusCode.OK,
                                    message = newEntry
                                )
                            }
                        }
                    }

                }?: kotlin.run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }
            }?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
        }
    }
}