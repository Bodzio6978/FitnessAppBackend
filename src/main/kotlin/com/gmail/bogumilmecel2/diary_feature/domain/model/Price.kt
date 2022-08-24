package com.gmail.bogumilmecel2.diary_feature.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val id:Int,
    val price: Double = 0.0,
    val forWhat: Int = 0
)