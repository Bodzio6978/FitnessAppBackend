package com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry

import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class DiaryEntryDto(
    @BsonId val _id: ObjectId = ObjectId(),
    val name: String,
    val unit: String,
    val nutritionValues: NutritionValues,
    val timestamp: Long,
    val date: String,
    var weight: Int,
    val mealName: String,
    val userId: ObjectId,
    val product: Product
)