package com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values

import org.ktorm.entity.Entity

interface NutritionValuesEntity:Entity<NutritionValuesEntity> {
    val id:Int
    val calories:Int
    val carbohydrates:Double
    val protein:Double
    val fat:Double
}