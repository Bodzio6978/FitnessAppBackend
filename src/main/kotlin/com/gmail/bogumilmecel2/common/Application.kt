package com.gmail.bogumilmecel2.common

import com.gmail.bogumilmecel2.authentication.data.repository.AuthenticationRepositoryImp
import com.gmail.bogumilmecel2.authentication.data.service.JwtTokenService
import com.gmail.bogumilmecel2.authentication.data.service.SHA256HashingService
import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenConfig
import com.gmail.bogumilmecel2.authentication.domain.use_case.AuthRoutes
import com.gmail.bogumilmecel2.authentication.domain.use_case.GetUserByUsername
import com.gmail.bogumilmecel2.authentication.domain.use_case.RegisterNewUser
import com.gmail.bogumilmecel2.authentication.routes.configureAuthRoutes
import com.gmail.bogumilmecel2.common.data.database.DatabaseManager
import com.gmail.bogumilmecel2.common.plugins.configureAuthentication
import com.gmail.bogumilmecel2.common.plugins.configureLocations
import com.gmail.bogumilmecel2.common.plugins.configureMonitoring
import com.gmail.bogumilmecel2.common.plugins.configureSerialization
import com.gmail.bogumilmecel2.diary_feature.data.repository.DiaryRepositoryImp
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.DeleteDiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.DiaryUseCases
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.GetDiaryEntries
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.InsertDiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.GetProductHistory
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.GetProducts
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.InsertProduct
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.ProductUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.configureDiaryRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val databaseManager = DatabaseManager()

    val diaryRepository = DiaryRepositoryImp(
        database = databaseManager.ktormDatabase
    )

    val productUseCases = ProductUseCases(
        insertProduct = InsertProduct(diaryRepository),
        getProducts = GetProducts(diaryRepository),
        getProductHistory = GetProductHistory(diaryRepository)
    )

    val diaryUseCases = DiaryUseCases(
        getDiaryEntries = GetDiaryEntries(diaryRepository),
        insertDiaryEntry = InsertDiaryEntry(diaryRepository),
        deleteDiaryEntry = DeleteDiaryEntry(diaryRepository)
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
        tokenConfig = tokenConfig
    )
    configureMonitoring()
    configureSerialization()
    configureLocations()



    routing {
        configureDiaryRoutes(
            productUseCases = productUseCases,
            diaryUseCases = diaryUseCases
        )

        configureAuthRoutes(
            tokenConfig = tokenConfig,
            authRoutes = AuthRoutes(
                registerNewUser = RegisterNewUser(
                    authenticationRepository = authenticationRepository,
                    hashingService = hashingService
                ),
                getUserByUsername = GetUserByUsername(
                    authenticationRepository = authenticationRepository,
                    hashingService = hashingService,
                    tokenService = tokenService
                )
            )
        )

    }


}
