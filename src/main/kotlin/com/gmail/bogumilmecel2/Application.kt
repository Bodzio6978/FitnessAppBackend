package com.gmail.bogumilmecel2

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.gmail.bogumilmecel2.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureMonitoring()
        configureHTTP()
        configureSerialization()
        configureRouting()
    }.start(wait = true)
}
