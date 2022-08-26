package com.gmail.bogumilmecel2.diary_feature.domain.model.price

import org.ktorm.entity.Entity

interface PriceEntity:Entity<PriceEntity> {
    val id:Int
    val value:Double
    val forHowMuch:Int
}