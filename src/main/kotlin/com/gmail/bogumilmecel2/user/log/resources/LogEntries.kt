package com.gmail.bogumilmecel2.user.log.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("")
class LogEntries(
    val timestamp:Long = System.currentTimeMillis()
)