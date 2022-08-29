package com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValuesEntity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int

object NutritionValuesTable:Table<NutritionValuesEntity>(tableName = "nutritionValues") {
    val id = int("id").primaryKey().bindTo { it.id }
    val calories = int("calories").bindTo { it.calories }
    val carbohydrates = double("carbohydrates").bindTo { it.carbohydrates }
    val protein = double("protein").bindTo { it.protein }
    val fat = double("fat").bindTo { it.fat }
}