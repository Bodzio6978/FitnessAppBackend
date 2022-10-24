package com.gmail.bogumilmecel2.user.user_data.routes

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.user_data.domain.use_cases.SaveUserNutritionValues
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureSaveUserNutritionValuesRoute(saveUserNutritionValues: SaveUserNutritionValues){
    authenticate {
        post("/nutritionValues/"){
            val nutritionValues = call.receiveOrNull<NutritionValues>()
            nutritionValues?.let {
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)?.toIntOrNull()
                principalId?.let { userId ->
                    val resource = saveUserNutritionValues(
                        nutritionValues = nutritionValues,
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
                                    HttpStatusCode.OK
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