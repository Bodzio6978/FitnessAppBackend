package com.gmail.bogumilmecel2.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.text.get


fun Route.productRouting() {
    route("/product"){
        get {
            call.respondText("hello")
        }
    }
}

fun Application.registerCustomerRoutes() {
    routing {
        productRouting()
    }
}
