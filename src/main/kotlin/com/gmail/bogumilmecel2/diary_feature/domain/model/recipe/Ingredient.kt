package com.gmail.bogumilmecel2.diary_feature.domain.model.recipe

import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val id: Int = -1,
    val weight: Int = 0,
    val product: Product = Product()
)
