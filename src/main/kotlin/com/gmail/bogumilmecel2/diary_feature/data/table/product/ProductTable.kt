package com.gmail.bogumilmecel2.diary_feature.data.table.product

import com.gmail.bogumilmecel2.diary_feature.domain.model.product.ProductEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object ProductTable: Table<ProductEntity>(tableName = "product"){
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val containerWeight = int("containerWeight").bindTo { it.containerWeight }
    val position = int("position").bindTo { it.position }
    val unit = varchar("unit").bindTo { it.unit }
    val nutritionValuesId = int("nutritionValuesId").bindTo { it.nutritionValuesId }
    val barcode = varchar("barcode").bindTo { it.barcode }
}