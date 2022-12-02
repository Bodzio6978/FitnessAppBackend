package com.gmail.bogumilmecel2.authentication.routes

import com.gmail.bogumilmecel2.authentication.domain.use_case.CheckIfUsernameExists
import com.gmail.bogumilmecel2.authentication.resources.CheckIfUsernameExistsResource
import com.gmail.bogumilmecel2.common.util.Resource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureCheckIfUsernameExistsRoute(
    checkIfUsernameExists: CheckIfUsernameExists
){
    get<CheckIfUsernameExistsResource> { usernameResource ->
        val username = usernameResource.username

        when(checkIfUsernameExists(username = username)){
                is Resource.Success -> {
                    call.respond(
                        status = HttpStatusCode.OK,
                        message = true
                    )
                }
                is Resource.Error -> {
                    call.respond(HttpStatusCode.Conflict)
                    return@get
                }
            }
    }
}