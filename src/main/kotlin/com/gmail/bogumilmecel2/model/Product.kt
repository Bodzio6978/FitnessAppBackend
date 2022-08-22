package com.gmail.bogumilmecel2.model

data class Product(
    val name: String = "White rice",
    val containerWeight: Int = 0,
    val position: Int = 0,
    val unit: String = "g",
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val prices: List<Price> = emptyList()
)
