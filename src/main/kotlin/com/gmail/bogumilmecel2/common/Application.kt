package com.gmail.bogumilmecel2.common

import com.gmail.bogumilmecel2.authentication.data.repository.AuthenticationRepositoryImp
import com.gmail.bogumilmecel2.common.data.database.DatabaseManager
import com.gmail.bogumilmecel2.common.plugins.*
import io.ktor.server.application.*
import com.gmail.bogumilmecel2.common.plugins.configureAuthentication
import com.gmail.bogumilmecel2.diary_feature.data.repository.DiaryRepositoryImp
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.GetProducts
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.InsertProduct
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.ProductUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.registerProductRoutes
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(){
    val databaseManager = DatabaseManager()

    val diaryRepository = DiaryRepositoryImp(
        database = databaseManager.ktormDatabase
    )

    val productUseCases = ProductUseCases(
        insertProduct = InsertProduct(diaryRepository),
        getProducts = GetProducts(diaryRepository)
    )

    val authenticationRepository = AuthenticationRepositoryImp(
        database = databaseManager.ktormDatabase
    )

    configureAuthentication(
        authenticationRepository = authenticationRepository
    )
    configureMonitoring()
    configureSerialization()

    routing {
        registerProductRoutes(
            productUseCases = productUseCases
        )
    }


}
