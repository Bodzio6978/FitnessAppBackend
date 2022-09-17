package com.gmail.bogumilmecel2.user.log.data.table

import com.gmail.bogumilmecel2.user.log.domain.model.LogEntryEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.long

object LogTable: Table<LogEntryEntity>(tableName = "logTable") {
    val id = int("id").primaryKey().bindTo { it.id }
    val timestamp = long("timestamp").bindTo { it.timestamp }
    val userId = int("userId").bindTo { it.userId }
    val streak = int("streak").bindTo { it.streak }
}