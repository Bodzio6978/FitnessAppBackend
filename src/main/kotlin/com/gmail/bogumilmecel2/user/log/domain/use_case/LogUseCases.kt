package com.gmail.bogumilmecel2.user.log.domain.use_case

data class LogUseCases(
    val getLatestLogEntry: GetLatestLogEntry,
    val insertLogEntry:InsertLogEntry
)
