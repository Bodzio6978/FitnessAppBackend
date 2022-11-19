package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import org.bson.types.ObjectId

data class RecipeDto(
    val id: ObjectId = ObjectId(),
    val name: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val timeNeeded: Int = 0,
    val difficulty: Int,
    val servings: Int,
    val userId: ObjectId,
    val username: String
)
