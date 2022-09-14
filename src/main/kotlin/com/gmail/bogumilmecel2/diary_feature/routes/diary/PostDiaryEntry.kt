package com.gmail.bogumilmecel2.diary_feature.routes.diary

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.InsertDiaryEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configurePostDiaryEntryRoute(
    insertDiaryEntry: InsertDiaryEntry
) {
    authenticate {
        post("/diaryEntries") {
            val callDiaryEntry = call.receiveOrNull<DiaryEntry>()
            callDiaryEntry?.let { diaryEntry ->
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)?.toIntOrNull()
                principalId?.let { userId ->
                    val resource = insertDiaryEntry(
                        diaryEntry = diaryEntry,
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