package com.gmail.bogumilmecel2.common

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
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
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.recipe.AddNewRecipe
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.recipe.RecipeUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.configureDiaryRoutes
import com.gmail.bogumilmecel2.user.log.domain.use_case.CheckLatestLogEntry
import com.gmail.bogumilmecel2.user.log.domain.use_case.GetLatestLogEntry
import com.gmail.bogumilmecel2.user.log.domain.use_case.InsertLogEntry
import com.gmail.bogumilmecel2.user.log.routes.configureLogRoutes
import com.gmail.bogumilmecel2.user.user_data.data.repository.UserRepositoryImp
import com.gmail.bogumilmecel2.user.user_data.domain.use_cases.*
import com.gmail.bogumilmecel2.user.user_data.routes.configureUserDataRoutes
import com.gmail.bogumilmecel2.user.weight.data.repository.WeightRepositoryImp
import com.gmail.bogumilmecel2.user.weight.domain.use_case.AddWeightEntry
import com.gmail.bogumilmecel2.user.weight.domain.use_case.GetLatestWeightEntries
import com.gmail.bogumilmecel2.user.weight.domain.use_case.WeightUseCases
import com.gmail.bogumilmecel2.user.weight.routes.configureWeightRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
    val rootLogger = loggerContext.getLogger("org.mongodb.driver")
    rootLogger.level = Level.OFF

    val databaseManager = DatabaseManager()

    val diaryRepository = DiaryRepositoryImp(
        recipeCol = databaseManager.client.getCollection("recipe_collection"),
        productCol = databaseManager.client.getCollection("product_collection"),
        diaryCol = databaseManager.client.getCollection("diary_collection")
    )

    val weightRepository = WeightRepositoryImp(
        weightCol = databaseManager.client.getCollection("weight_collection")
    )

    val userRepository = UserRepositoryImp(userCol = databaseManager.client.getCollection("user_collection"))

    val productUseCases = ProductUseCases(
        insertProduct = InsertProduct(diaryRepository),
        getProducts = GetProducts(diaryRepository),
        getProductHistory = GetProductHistory(diaryRepository),
        searchForProductWithBarcode = SearchForProductWithBarcode(diaryRepository),
        addNewPrice = AddNewPrice(diaryRepository)
    )

    val getLatestLogEntry = GetLatestLogEntry(userRepository)
    val insertLogEntry = InsertLogEntry(userRepository)

    val checkLatestLogEntry = CheckLatestLogEntry(
        getLatestLogEntry = getLatestLogEntry,
        insertLogEntry = insertLogEntry
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

    val userDataUseCases = UserDataUseCases(
        getUserInformation = GetUserInformation(userRepository = userRepository),
        getUserNutritionValues = GetUserNutritionValues(userRepository = userRepository),
        saveUserInformation = SaveUserInformation(userRepository = userRepository),
        saveUserNutritionValues = SaveUserNutritionValues(userRepository = userRepository)
    )

    val recipeUseCases = RecipeUseCases(
        addNewRecipe = AddNewRecipe(diaryRepository = diaryRepository)
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
            diaryUseCases = diaryUseCases,
            recipeUseCases = recipeUseCases
        )

        configureAuthRoutes(
            tokenConfig = tokenConfig,
            authRoutes = AuthRoutes(
                registerNewUser = RegisterNewUser(
                    userRepository = userRepository,
                    hashingService = hashingService
                ),
                getUserByUsername = GetUserByUsername(
                    userRepository = userRepository,
                    hashingService = hashingService,
                    tokenService = tokenService
                )
            )
        )

        configureLogRoutes(
            checkLatestLogEntry = checkLatestLogEntry
        )

        configureWeightRoutes(
            weightUseCases = weightUseCases
        )

        configureUserDataRoutes(
            userDataUseCases = userDataUseCases
        )
    }
}
