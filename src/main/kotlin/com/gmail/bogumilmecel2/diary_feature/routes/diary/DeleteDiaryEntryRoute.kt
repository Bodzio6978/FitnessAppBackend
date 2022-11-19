package com.gmail.bogumilmecel2.diary_feature.routes.diary

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.DeleteDiaryEntry
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureDeleteDiaryEntryRoute(
    deleteDiaryEntry: DeleteDiaryEntry
){
    authenticate {
        delete("/{diaryEntryId}") {
            val diaryEntryId = call.parameters["diaryEntryId"]
            diaryEntryId?.let { entryId ->
                val principal = call.principal<JWTPrincipal>()
                val principalId = principal?.getClaim("userId", String::class)
                principalId?.let { userId ->
                    val resource = deleteDiaryEntry(
                        diaryEntryId = entryId,
                        userId = userId
                    )

                    when(resource){
                        is Resource.Error -> {
                            call.respond(
                                HttpStatusCode.Conflict
                            )
                        }
                        is Resource.Success -> {
                            call.respond(
                                HttpStatusCode.OK,
                                message = true
                            )
                        }
                    }

                }?: kotlin.run {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@delete
                }
            }?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
        }
    }
}