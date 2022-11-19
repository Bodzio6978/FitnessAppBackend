package com.gmail.bogumilmecel2.user.log.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class LogEntry(
    val streak:Int = 0,
    val timestamp:Long = System.currentTimeMillis()
)
