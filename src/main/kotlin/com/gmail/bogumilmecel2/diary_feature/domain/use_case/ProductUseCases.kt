package com.gmail.bogumilmecel2.diary_feature.domain.use_case

import com.gmail.bogumilmecel2.diary_feature.domain.use_case.GetProducts
import com.gmail.bogumilmecel2.diary_feature.domain.use_case.InsertProduct

data class ProductUseCases(
    val getProducts: GetProducts,
    val insertProduct: InsertProduct
)
