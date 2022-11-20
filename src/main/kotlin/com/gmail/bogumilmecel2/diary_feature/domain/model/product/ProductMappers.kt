package com.gmail.bogumilmecel2.diary_feature.domain.model.product

import com.gmail.bogumilmecel2.common.util.extensions.toObjectId

fun Product.toDto(userId: String): ProductDto = ProductDto(
    _id = id.toObjectId(),
    name = name,
    containerWeight = containerWeight,
    position = position,
    unit = unit,
    nutritionValues = nutritionValues,
    barcode = barcode,
    price = price,
    creatorId = userId.toObjectId(),
    creatorUsername = username,
    timestamp = timestamp
)

fun ProductDto.toProduct(): Product = Product(
    id = _id.toString(),
    name = name,
    containerWeight = containerWeight,
    position = position,
    unit = unit,
    nutritionValues = nutritionValues,
    barcode = barcode,
    price = price,
    userId = creatorId.toString(),
    username = creatorUsername,
    timestamp = timestamp
)