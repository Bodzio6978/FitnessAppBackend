package com.gmail.bogumilmecel2.diary_feature.domain.use_case.product

data class ProductUseCases(
    val getProducts: GetProducts,
    val insertProduct: InsertProduct,
    val getProductHistory: GetProductHistory,
    val searchForProductWithBarcode: SearchForProductWithBarcode
)
