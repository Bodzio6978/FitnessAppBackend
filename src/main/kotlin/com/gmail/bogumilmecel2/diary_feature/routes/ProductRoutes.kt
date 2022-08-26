package com.gmail.bogumilmecel2.diary_feature.routes

import com.gmail.bogumilmecel2.common.data.database.DatabaseManager
import com.gmail.bogumilmecel2.diary_feature.data.repository.DiaryRepositoryImp
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.text.get


fun Route.productRouting() {

    val databaseManager = DatabaseManager()
    val diaryRepository = DiaryRepositoryImp(
        database = databaseManager.ktormDatabase
    )
    val scope = CoroutineScope(Job() + Dispatchers.IO)



    route("products"){
        get("") {
            val text = "huj"

                val items = diaryRepository.getProducts(text)
                call.respond(
                    items
                )

        }
//        post("") {
//            val product = call.receive<Product>()
//            scope.launch {
//                diaryRepository.insertProduct(product)
//            }
//        }
    }
}

fun Application.registerProductRoutes() {
    routing {
        productRouting()
    }
}
