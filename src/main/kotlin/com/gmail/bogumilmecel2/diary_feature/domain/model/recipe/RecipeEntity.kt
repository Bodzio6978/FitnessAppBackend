package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import org.ktorm.entity.Entity

interface RecipeEntity : Entity<RecipeEntity> {
    val id:Int
    val name: String
    val userId: Int
    val timestamp: Long
    val imageUrl: String?
    val timeNeeded: Int
    val difficulty: Int
    val servings: Int
}