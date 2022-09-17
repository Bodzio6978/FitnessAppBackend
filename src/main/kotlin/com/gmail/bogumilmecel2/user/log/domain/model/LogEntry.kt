package com.gmail.bogumilmecel2.user.log.domain.model

data class LogEntry(
    val id:Int,
    val timestamp:Long,
    val streak:Int
)
