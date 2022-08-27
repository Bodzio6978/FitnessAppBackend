package com.gmail.bogumilmecel2.diary_feature.domain.model.product

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id:Int,
    val name: String = "White rice",
    val containerWeight: Int = 0,
    val position: Int = 0,
    val unit: String = "g",
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val price: Price
)


