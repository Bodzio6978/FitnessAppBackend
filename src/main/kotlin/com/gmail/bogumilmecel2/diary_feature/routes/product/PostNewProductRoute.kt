package com.gmail.bogumilmecel2.diary_feature.routes.product

import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.InsertProduct
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configurePostNewProductRoute(
    insertNewProduct: InsertProduct
){
    post {
        val product = call.receiveOrNull<Product>()
        product?.let {
            val newProduct = insertNewProduct(product)

            call.respond(newProduct.data!!)

        }?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
    }
}