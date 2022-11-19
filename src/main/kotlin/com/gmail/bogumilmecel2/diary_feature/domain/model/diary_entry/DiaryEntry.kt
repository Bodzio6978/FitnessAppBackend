package com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntry(
    val id: String? = null,
    val name: String,
    val unit: String,
    val nutritionValues: NutritionValues,
    val timestamp: Long,
    val date: String,
    var weight: Int,
    val mealName: String,
    val product: Product
)
