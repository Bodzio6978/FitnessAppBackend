package com.gmail.bogumilmecel2.diary_feature.data.table

import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.IngredientEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int

object IngredientTable: Table<IngredientEntity>(tableName = "ingredient"){
    val id = int("id").primaryKey().bindTo { it.id }
    val weight = int("weight").bindTo { it.weight }
    val productId = int("productId").bindTo { it.productId }
    val recipeId = int("recipeId").bindTo { it.recipeId }
}