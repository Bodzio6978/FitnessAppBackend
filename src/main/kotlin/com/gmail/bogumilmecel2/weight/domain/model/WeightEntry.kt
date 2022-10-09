package com.gmail.bogumilmecel2.weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightEntry(
    val id:Int = -1,
    val value: Double,
    val timestamp:Long
)