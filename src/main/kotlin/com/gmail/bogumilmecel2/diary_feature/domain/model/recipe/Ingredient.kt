package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val weight: Int = 0,
    val productId: String,
    val unit: String,
    val nutritionValues: NutritionValues
)
