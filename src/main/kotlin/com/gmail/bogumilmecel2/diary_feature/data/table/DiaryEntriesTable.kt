package com.gmail.bogumilmecel2.diary_feature.data.table

import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntryEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long
import org.ktorm.schema.varchar

object DiaryEntriesTable:Table<DiaryEntryEntity>(tableName = "diaryEntry") {
    val id = int("id").primaryKey().bindTo { it.id }
    val mealName = varchar("mealName").bindTo { it.mealName }
    val timestamp = long("timestamp").bindTo { it.timestamp }
    val userId = int("userId").bindTo { it.userId }
    val date = varchar("date").bindTo { it.date }
    val weight = int("weight").bindTo { it.weight }
    val productId = int("productId").bindTo { it.productId }
}