package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String = "",
    val name: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val timeNeeded: Int = 0,
    val difficulty: Int = 0,
    val servings: Int = 0,
    val username: String
)
