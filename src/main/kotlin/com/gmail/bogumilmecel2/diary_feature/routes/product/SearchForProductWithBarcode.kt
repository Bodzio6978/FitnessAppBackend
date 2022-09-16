package com.gmail.bogumilmecel2.diary_feature.routes.product

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.SearchForProductWithBarcode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureSearchForProductWithBarcodeRoute(
    searchForProductWithBarcode: SearchForProductWithBarcode
){
    get("/{barcode}") {
        val barcode = call.parameters["barcode"]
        barcode?.let {
            val resource = searchForProductWithBarcode(
                barcode = barcode
            )

            when(resource){
                is Resource.Success -> {
                    resource.data?.let {product ->
                        call.respond(
                            HttpStatusCode.OK,
                            message = product
                        )
                    }?: kotlin.run {
                        call.respond(
                            HttpStatusCode.NotFound
                        )
                        return@get
                    }

                }
                is Resource.Error -> {
                    call.respond(
                        HttpStatusCode.Conflict
                    )
                    return@get
                }
            }
        }?: kotlin.run {
            call.respond(
                HttpStatusCode.BadRequest
            )
            return@get
        }
    }
}