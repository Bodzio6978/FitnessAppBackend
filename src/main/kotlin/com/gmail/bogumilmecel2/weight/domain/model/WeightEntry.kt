package com.gmail.bogumilmecel2.weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightEntry(
    val id:String? = null,
    val value: Double,
    val timestamp:Long
)