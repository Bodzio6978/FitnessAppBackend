package com.gmail.bogumilmecel2.user.log.routes

import com.gmail.bogumilmecel2.user.log.domain.use_case.LogUseCases
import io.ktor.server.routing.*

fun Route.configureLogRoutes(
    logUseCases: LogUseCases
){
    route("/logEntries"){
        configureGetLatestLogEntryRoute(logUseCases.getLatestLogEntry)
        configurePostLogEntryRoute(logUseCases.insertLogEntry)
    }
}