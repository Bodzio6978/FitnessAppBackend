package com.gmail.bogumilmecel2.common.util.extensions

import com.gmail.bogumilmecel2.diary_feature.data.table.NutritionValuesTable
import com.gmail.bogumilmecel2.diary_feature.data.table.PriceTable
import com.gmail.bogumilmecel2.diary_feature.data.table.ProductTable
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import org.ktorm.database.Database
import org.ktorm.dsl.*

fun QueryRowSet.mapProduct(
    price: Price? = null
): Product {
    return Product(
        id = this[ProductTable.id] ?: -1,
        name = this[ProductTable.name] ?: "",
        containerWeight = this[ProductTable.containerWeight] ?: 0,
        position = this[ProductTable.position] ?: 0,
        unit = this[ProductTable.unit] ?: "",
        price = price,
        nutritionValues = NutritionValues(
            id = this[NutritionValuesTable.id] ?: 0,
            calories = this[NutritionValuesTable.calories] ?: 0,
            carbohydrates = this[NutritionValuesTable.carbohydrates] ?: 0.0,
            protein = this[NutritionValuesTable.protein] ?: 0.0,
            fat = this[NutritionValuesTable.fat] ?: 0.0
        ),
        barcode = this[ProductTable.barcode]
    )
}

fun QueryRowSet.mapFirstPrice(
    productId: Int,
    database: Database
): Price? {
    return database.from(PriceTable)
        .select()
        .where {
            PriceTable.productId eq productId
        }.map { priceRowSet ->
            Price(
                id = priceRowSet[PriceTable.id] ?: -1,
                value = priceRowSet[PriceTable.value] ?: 0.0,
                currency = priceRowSet[PriceTable.currency] ?: ""
            )
        }.firstOrNull()
}