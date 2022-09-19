package com.gmail.bogumilmecel2.user.log.domain.model

data class LogEntry(
    val id:Int = 0,
    val timestamp:Long = System.currentTimeMillis(),
    val streak:Int = 1
)
