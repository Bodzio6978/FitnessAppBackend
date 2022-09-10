package com.gmail.bogumilmecel2.common

import com.gmail.bogumilmecel2.authentication.data.repository.AuthenticationRepositoryImp
import com.gmail.bogumilmecel2.authentication.data.service.JwtTokenService
import com.gmail.bogumilmecel2.authentication.data.service.SHA256HashingService
import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenConfig
import com.gmail.bogumilmecel2.authentication.routes.authenticationRoute
import com.gmail.bogumilmecel2.common.data.database.DatabaseManager
import com.gmail.bogumilmecel2.common.plugins.*
import io.ktor.server.application.*
import com.gmail.bogumilmecel2.common.plugins.configureAuthentication
import com.gmail.bogumilmecel2.diary_feature.data.repository.DiaryRepositoryImp
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.GetProducts
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.InsertProduct
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.ProductUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.configureFeatureDiaryRoutes
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
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

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("ktor.jwt.issuer").getString(),
        audience = environment.config.property("ktor.jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    val hashingService = SHA256HashingService()

    configureAuthentication(
        authenticationRepository = authenticationRepository,
        tokenConfig = tokenConfig
    )
    configureMonitoring()
    configureSerialization()

    routing {
        configureFeatureDiaryRoutes(
            productUseCases = productUseCases
        )

        authenticationRoute(
            tokenConfig = tokenConfig,
            tokenService = tokenService,
            hashingService = hashingService,
            authenticationRepository = authenticationRepository
        )

    }


}
