package com.gmail.bogumilmecel2.user.weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightEntry(
    val id:String? = null,
    val value: Double,
    val timestamp:Long
)