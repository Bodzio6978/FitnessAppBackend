package com.gmail.bogumilmecel2.diary_feature.routes

import com.gmail.bogumilmecel2.diary_feature.domain.use_case.ProductUseCases
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.DiaryUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.diary.configureGetDiaryEntriesRoute
import com.gmail.bogumilmecel2.diary_feature.routes.diary.configurePostDiaryEntryRoute
import com.gmail.bogumilmecel2.diary_feature.routes.product.configurePostNewProductRoute
import com.gmail.bogumilmecel2.diary_feature.routes.product.configureSearchForProductWithTextRoute
import io.ktor.server.routing.*


fun Route.configureDiaryRoutes(
    productUseCases: ProductUseCases,
    diaryUseCases: DiaryUseCases
) {
    route("/products") {
        configurePostNewProductRoute(productUseCases.insertProduct)
        configureSearchForProductWithTextRoute(productUseCases.getProducts)
    }

    route("/diaryEntries"){
        configurePostDiaryEntryRoute(diaryUseCases.insertDiaryEntry)
        configureGetDiaryEntriesRoute(diaryUseCases.getDiaryEntries)
    }
}
