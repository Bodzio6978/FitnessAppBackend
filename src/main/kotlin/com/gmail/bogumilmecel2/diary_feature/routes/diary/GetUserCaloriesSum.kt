package com.gmail.bogumilmecel2.diary_feature.routes.diary

import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.GetUserCaloriesSum
import com.gmail.bogumilmecel2.diary_feature.resources.CaloriesSum
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGetUserCaloriesSumRoute(
    getUserCaloriesSum: GetUserCaloriesSum
){
    authenticate {
        get<CaloriesSum> {
            val date = it.date

            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)

            principalId?.let { userId ->
                val caloriesSumResponse = getUserCaloriesSum(
                    date = date,
                    userId = userId
                )

                call.respond(
                    HttpStatusCode.OK,
                    message = caloriesSumResponse
                )
            }?: kotlin.run {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }
        }
    }
}