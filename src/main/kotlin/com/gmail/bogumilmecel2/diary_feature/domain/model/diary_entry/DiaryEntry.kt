package com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry

import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntry(
    val id:Int,
    val product: Product,
    val timeStamp:Long,
    var weight:Double,
    val mealName:String
)