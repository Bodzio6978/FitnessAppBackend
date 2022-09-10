package com.gmail.bogumilmecel2.diary_feature.routes

import com.gmail.bogumilmecel2.diary_feature.domain.use_case.ProductUseCases
import com.gmail.bogumilmecel2.diary_feature.routes.product.configurePostNewProductRoute
import com.gmail.bogumilmecel2.diary_feature.routes.product.configureSearchForProductWithTextRoute
import io.ktor.server.routing.*


fun Route.configureFeatureDiaryRoutes(
    productUseCases: ProductUseCases,
) {
    route("products") {
        configurePostNewProductRoute(productUseCases.insertProduct)
        configureSearchForProductWithTextRoute(productUseCases.getProducts)
    }
}
