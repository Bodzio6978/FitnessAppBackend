package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import org.ktorm.entity.Entity

interface IngredientEntity : Entity<IngredientEntity> {
    val id: Int
    val weight: Int
    val productId: Int
    val recipeId: Int
}