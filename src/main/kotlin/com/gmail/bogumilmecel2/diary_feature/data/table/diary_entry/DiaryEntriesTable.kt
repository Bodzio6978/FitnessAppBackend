package com.gmail.bogumilmecel2.diary_feature.data.table.diary_entry

import com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values.NutritionValuesTable.bindTo
import com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values.NutritionValuesTable.primaryKey
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntryEntity
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValuesEntity
import org.ktorm.schema.*

object DiaryEntriesTable:Table<DiaryEntryEntity>(tableName = "diaryEntries") {
    val id = int("id").primaryKey().bindTo { it.id }
    val mealName = varchar("mealName").bindTo { it.mealName }
    val timestamp = long("timestamp").bindTo { it.timestamp }
    val weight = int("weight").bindTo { it.weight }
}