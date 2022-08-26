package com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values

import kotlinx.serialization.Serializable

@Serializable
data class NutritionValues(
    val id:Int = 0,
    val calories:Int = 285,
    val carbohydrates:Double = 100.0,
    val protein:Double = 50.0,
    val fat:Double = 10.0
)
