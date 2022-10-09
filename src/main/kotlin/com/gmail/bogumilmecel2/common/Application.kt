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
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.*
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.*
import com.gmail.bogumilmecel2.diary_feature.routes.configureDiaryRoutes
import com.gmail.bogumilmecel2.user.log.data.repository.LogRepositoryImp
import com.gmail.bogumilmecel2.user.log.domain.use_case.GetLatestLogEntry
import com.gmail.bogumilmecel2.user.log.domain.use_case.InsertLogEntry
import com.gmail.bogumilmecel2.user.log.domain.use_case.LogUseCases
import com.gmail.bogumilmecel2.user.log.routes.configureLogRoutes
import com.gmail.bogumilmecel2.weight.data.repository.WeightRepositoryImp
import com.gmail.bogumilmecel2.weight.domain.use_case.AddWeightEntry
import com.gmail.bogumilmecel2.weight.domain.use_case.GetLatestWeightEntries
import com.gmail.bogumilmecel2.weight.domain.use_case.WeightUseCases
import com.gmail.bogumilmecel2.weight.routes.configureWeightRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val databaseManager = DatabaseManager()

    val diaryRepository = DiaryRepositoryImp(
        database = databaseManager.ktormDatabase
    )

    val logRepository = LogRepositoryImp(
        database = databaseManager.ktormDatabase
    )

    val weightRepository = WeightRepositoryImp(
        database = databaseManager.ktormDatabase
    )

    val productUseCases = ProductUseCases(
        insertProduct = InsertProduct(diaryRepository),
        getProducts = GetProducts(diaryRepository),
        getProductHistory = GetProductHistory(diaryRepository),
        searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
        addNewPrice = AddNewPrice(diaryRepository)
    )

    val getLatestLogEntry = GetLatestLogEntry(logRepository)

    val logUseCases = LogUseCases(
        getLatestLogEntry = getLatestLogEntry,
        insertLogEntry = InsertLogEntry(
            logRepository = logRepository,
            getLatestLogEntry = getLatestLogEntry
        )
    )

    val diaryUseCases = DiaryUseCases(
        getDiaryEntries = GetDiaryEntries(diaryRepository),
        insertDiaryEntry = InsertDiaryEntry(diaryRepository),
        deleteDiaryEntry = DeleteDiaryEntry(diaryRepository),
        getUserCaloriesSum = GetUserCaloriesSum(diaryRepository)
    )

    val weightUseCases = WeightUseCases(
        addWeightEntry = AddWeightEntry(weightRepository),
        getLatestWeightEntries = GetLatestWeightEntries(weightRepository)
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

        configureLogRoutes(
            logUseCases = logUseCases
        )

        configureWeightRoutes(
            weightUseCases = weightUseCases
        )
    }
}
