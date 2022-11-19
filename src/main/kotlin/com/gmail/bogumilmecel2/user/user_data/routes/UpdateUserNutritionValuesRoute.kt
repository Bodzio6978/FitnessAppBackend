package com.gmail.bogumilmecel2.user.user_data.routes

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.user_data.domain.use_cases.UpdateUserNutritionValues
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureUpdateUserNutritionValuesRoute(updateUserNutritionValues: UpdateUserNutritionValues) {
    authenticate {
        put("/nutritionValues/") {
            val nutritionValues = call.receiveOrNull<NutritionValues>()
            nutritionValues?.let {
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)
                principalId?.let { userId ->
                    val resource = updateUserNutritionValues(
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
                                return@put
                            }
                        }

                        is Resource.Error -> {
                            call.respond(
                                HttpStatusCode.Conflict
                            )
                            return@put
                        }
                    }
                } ?: kotlin.run {
                    call.respond(
                        HttpStatusCode.Unauthorized
                    )
                    return@put
                }
            } ?: kotlin.run {
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@put
            }
        }
    }
}