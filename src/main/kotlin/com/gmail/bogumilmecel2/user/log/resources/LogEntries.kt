package com.gmail.bogumilmecel2.user.log.resources

import com.gmail.bogumilmecel2.user.log.domain.model.LogRequest
import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("")
class LogEntries(
    val logRequest: LogRequest = LogRequest()
)