package com.gmail.bogumilmecel2.common.util.extensions

import com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values.NutritionValuesTable
import com.gmail.bogumilmecel2.diary_feature.data.table.price.PriceTable
import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import org.ktorm.dsl.QueryRowSet

fun QueryRowSet.mapProduct():Product{
    return Product(
        id = this[ProductTable.id] ?: -1,
        name = this[ProductTable.name] ?: "",
        containerWeight = this[ProductTable.containerWeight] ?: 0,
        position = this[ProductTable.position] ?: 0,
        unit = this[ProductTable.unit] ?: "",
        price = Price(
            id = this[PriceTable.id] ?: 0,
            value = this[PriceTable.value] ?: 0.0,
            forHowMuch = this[PriceTable.forHowMuch] ?: 0
        ),
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