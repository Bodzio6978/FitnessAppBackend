package com.gmail.bogumilmecel2.diary_feature.domain.model.price

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val id:Int,
    val value: Double = 0.0,
    val forHowMuch: Int = 0
)