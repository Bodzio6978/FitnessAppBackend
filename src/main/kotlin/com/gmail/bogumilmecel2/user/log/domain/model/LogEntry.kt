package com.gmail.bogumilmecel2.user.log.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LogEntry(
    val id:Int = 0,
    val streak:Int = 0,
    val timestamp:Long = System.currentTimeMillis()
)
