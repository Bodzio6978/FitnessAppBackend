package com.gmail.bogumilmecel2.diary_feature.routes.product

import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.InsertProduct
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configurePostNewProductRoute(
    insertNewProduct: InsertProduct
) {
    authenticate {
        post {
            val principal = call.principal<JWTPrincipal>()
            val principalId = principal?.getClaim("userId", String::class)
            val product = call.receiveOrNull<Product>()
            principalId?.let {
                product?.let {
                    val newProduct = insertNewProduct(product, principalId)

                    call.respond(newProduct.data!!)

                } ?: kotlin.run {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }
            }
        }
    }
}