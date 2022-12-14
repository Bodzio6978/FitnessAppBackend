package com.gmail.bogumilmecel2.common.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*

fun Application.configureMonitoring() {
    install(CallLogging) {
        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val userAgent = call.request.headers["User-Agent"]
            val huj1 = call.request.toString()
            "Status: $status, HTTP method: $httpMethod, User agent: $userAgent, request: $huj1"
        }
    }

}
