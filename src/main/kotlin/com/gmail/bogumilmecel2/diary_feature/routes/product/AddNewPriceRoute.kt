package com.gmail.bogumilmecel2.diary_feature.routes.product

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.AddNewPrice
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureAddNewPriceRoute(
    addNewPrice: AddNewPrice
) {
    post("/{productId}/prices") {
        val productId = call.parameters["productId"]
        val price = call.receiveOrNull<Price>()

        if (productId!=null && price != null) {
            val resource = addNewPrice(
                productId = productId,
                price = price
            )

            when (resource) {
                is Resource.Error -> {
                    call.respond(
                        HttpStatusCode.Conflict
                    )
                    return@post
                }

                is Resource.Success -> {
                    resource.data?.let { data ->
                        call.respond(
                            HttpStatusCode.OK,
                            message = data
                        )
                    } ?: kotlin.run {
                        call.respond(
                            HttpStatusCode.Conflict
                        )
                        return@post
                    }
                }
            }
        } else {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
    }
}