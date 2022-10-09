package com.gmail.bogumilmecel2.weight.domain.model

import org.ktorm.entity.Entity

interface WeightEntity : Entity<WeightEntity> {
    val id: Int
    val userId: Int
    val timestamp: Long
    val value: Double
}