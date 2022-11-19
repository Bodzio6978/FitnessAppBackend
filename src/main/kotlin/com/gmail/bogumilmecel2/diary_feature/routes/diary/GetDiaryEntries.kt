package com.gmail.bogumilmecel2.diary_feature.routes.diary

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.GetDiaryEntries
import com.gmail.bogumilmecel2.diary_feature.resources.DiaryEntries
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGetDiaryEntriesRoute(
    getDiaryEntries: GetDiaryEntries
){
    authenticate {
        get<DiaryEntries> { diaryEntry ->
            val date = diaryEntry.date

            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)

            principalId?.let { userId ->
                val resource = getDiaryEntries(
                    date = date,
                    userId = userId
                )

                when(resource){
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
                                HttpStatusCode.Conflict
                            )
                            return@get
                        }
                    }
                }
            }?: kotlin.run {
                call.respond(HttpStatusCode.Unauthorized)
                return@get
            }
        }
    }
}