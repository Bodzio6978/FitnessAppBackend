package com.gmail.bogumilmecel2.user.weight.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class WeightEntryDto(
    @BsonId val _id:ObjectId = ObjectId(),
    val value: Double,
    val timestamp:Long,
    val userId: ObjectId
)