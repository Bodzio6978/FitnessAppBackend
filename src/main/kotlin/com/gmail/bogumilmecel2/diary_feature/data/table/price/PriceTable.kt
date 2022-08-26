package com.gmail.bogumilmecel2.diary_feature.data.table.price

import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable.bindTo
import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable.primaryKey
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.PriceEntity
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.ProductEntity
import org.ktorm.schema.Table
import org.ktorm.schema.double
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object PriceTable: Table<PriceEntity>(tableName = "price"){
    val id = int("id").primaryKey().bindTo { it.id }
    val forHowMuch = int("forHowMuch").bindTo { it.forHowMuch }
    val value = double("value").bindTo { it.value }
}