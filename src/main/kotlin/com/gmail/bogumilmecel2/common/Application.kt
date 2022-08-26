package com.gmail.bogumilmecel2.common

import com.gmail.bogumilmecel2.common.plugins.configureHTTP
import com.gmail.bogumilmecel2.common.plugins.configureMonitoring
import com.gmail.bogumilmecel2.common.plugins.configureRouting
import com.gmail.bogumilmecel2.common.plugins.configureSerialization
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureMonitoring()
        configureHTTP()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
