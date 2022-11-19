package com.gmail.bogumilmecel2.diary_feature.routes.recipe

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.recipe.AddNewRecipe
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureNewRecipeRoute(
    addNewRecipe: AddNewRecipe
) {
    authenticate {
        post(""){
            val recipe = call.receiveOrNull<Recipe>()
            recipe?.let {
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)
                principalId?.let { userId ->
                    val resource = addNewRecipe(
                        recipe = recipe,
                        userId = userId
                    )
                    when (resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                call.respond(
                                    HttpStatusCode.OK,
                                    message = it
                                )
                            } ?: kotlin.run {
                                call.respond(
                                    HttpStatusCode.Conflict
                                )
                                return@post
                            }
                        }

                        is Resource.Error -> {
                            call.respond(
                                HttpStatusCode.Conflict
                            )
                            return@post
                        }
                    }
                } ?: kotlin.run {
                    call.respond(
                        HttpStatusCode.Unauthorized
                    )
                    return@post
                }
            } ?: kotlin.run {
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@post
            }

        }
    }
}