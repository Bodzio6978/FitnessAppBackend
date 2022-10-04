package com.gmail.bogumilmecel2.diary_feature.routes

import com.gmail.bogumilmecel2.diary_feature.domain.use_case.diary.DiaryUseCases
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.product.ProductUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.diary.configureDeleteDiaryEntryRoute
import com.gmail.bogumilmecel2.diary_feature.routes.diary.configureGetDiaryEntriesRoute
import com.gmail.bogumilmecel2.diary_feature.routes.diary.configureGetUserCaloriesSumRoute
import com.gmail.bogumilmecel2.diary_feature.routes.diary.configurePostDiaryEntryRoute
import com.gmail.bogumilmecel2.diary_feature.routes.product.*
import io.ktor.server.routing.*


fun Route.configureDiaryRoutes(
    productUseCases: ProductUseCases,
    diaryUseCases: DiaryUseCases
) {
    route("/products") {
        configurePostNewProductRoute(productUseCases.insertProduct)
        configureSearchForProductWithTextRoute(productUseCases.getProducts)
        configureGetProductHistoryRoute(productUseCases.getProductHistory)
        configureSearchForProductWithBarcodeRoute(productUseCases.searchForProductWithBarcode)
        configureAddNewPriceRoute(productUseCases.addNewPrice)
    }

    route("/diaryEntries"){
        configurePostDiaryEntryRoute(diaryUseCases.insertDiaryEntry)
        configureGetDiaryEntriesRoute(diaryUseCases.getDiaryEntries)
        configureDeleteDiaryEntryRoute(diaryUseCases.deleteDiaryEntry)
        configureGetUserCaloriesSumRoute(diaryUseCases.getUserCaloriesSum)
    }
}
