package com.gmail.bogumilmecel2.weight.data.table

import com.gmail.bogumilmecel2.weight.domain.model.WeightEntity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.long

object WeightTable : Table<WeightEntity>(tableName = "weightTable") {
    val id = int("id").primaryKey().bindTo { it.id }
    val timestamp = long("timestamp").bindTo { it.timestamp }
    val userId = int("userId").bindTo { it.userId }
    val value = double("value").bindTo { it.value }
}