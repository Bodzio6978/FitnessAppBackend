package com.gmail.bogumilmecel2.diary_feature.data.table

import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.RecipeEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object RecipeTable: Table<RecipeEntity>(tableName = "recipe"){
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val userId = int("userId").bindTo { it.userId }
    val timestamp = long("timestamp").bindTo { it.timestamp }
    val imageUrl = varchar("imageUrl").bindTo { it.imageUrl }
    val timeNeeded = int("timeNeeded").bindTo { it.timeNeeded }
    val difficulty = int("difficulty").bindTo { it.difficulty }
    val servings = int("servings").bindTo { it.servings }
}