package com.gmail.bogumilmecel2.diary_feature.domain.model.product

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class ProductDto(
    @BsonId val _id: ObjectId = ObjectId(),
    val name: String = "",
    val containerWeight: Int = 0,
    val position: Int = 0,
    val unit: String = "g",
    val timestamp:Long,
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val price: Price? = null,
    val creatorId: ObjectId,
    val creatorUsername: String
)
