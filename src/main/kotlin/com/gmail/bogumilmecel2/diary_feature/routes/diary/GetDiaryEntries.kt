package com.gmail.bogumilmecel2.diary_feature.routes.diary

import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.GetDiaryEntries
import com.gmail.bogumilmecel2.diary_feature.resources.DiaryEntries
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureGetDiaryEntriesRoute(
    getDiaryEntries: GetDiaryEntries
){
    authenticate {
        get<DiaryEntries> { diaryEntry ->
            val date = diaryEntry.date
            val diaryEntries = getDiaryEntries(
                date = date,
                userId = 1
            )
            call.respond(
                HttpStatusCode.OK,
                message = diaryEntries.data!!
            )
        }
    }
}