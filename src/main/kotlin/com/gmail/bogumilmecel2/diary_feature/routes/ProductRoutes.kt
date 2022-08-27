package com.gmail.bogumilmecel2.diary_feature.routes

import com.gmail.bogumilmecel2.common.data.database.DatabaseManager
import com.gmail.bogumilmecel2.common.exception.NoDatabaseEntryException
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.data.repository.DiaryRepositoryImp
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.GetProducts
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.InsertProduct
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.ProductUseCases
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

    val productUseCases = ProductUseCases(
        insertProduct = InsertProduct(diaryRepository),
        getProducts = GetProducts(diaryRepository)
    )

    route("products") {
        get("/{searchText}") {
            val searchText = call.parameters["searchText"]

            if (searchText==null){
                call.respond(HttpStatusCode.BadRequest, message = "Incorrect search text")
                return@get
            }

            val resource = productUseCases.getProducts(searchText)

            if (resource is Resource.Error){
                if (resource.error is NoDatabaseEntryException){
                    call.respond(HttpStatusCode.NotFound)
                }else{
                    call.respond(HttpStatusCode.BadRequest)
                }
                return@get
            }else{
                call.respond(resource.data!!)
            }


        }
    }
}

fun Application.registerProductRoutes() {
    routing {
        productRouting()
    }
}
