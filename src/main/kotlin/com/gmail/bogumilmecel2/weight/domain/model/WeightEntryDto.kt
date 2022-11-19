package com.gmail.bogumilmecel2.weight.domain.model

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class WeightEntryDto(
    @BsonId val id:ObjectId = ObjectId(),
    val value: Double,
    val timestamp:Long,
    val userId: ObjectId
)