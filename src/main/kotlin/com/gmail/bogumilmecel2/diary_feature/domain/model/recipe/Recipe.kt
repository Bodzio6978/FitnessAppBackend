package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

@kotlinx.serialization.Serializable
data class Recipe(
    val id: Int = -1,
    val name: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val timeNeeded: Int = 0,
    val difficulty: Int,
    val servings: Int
)
