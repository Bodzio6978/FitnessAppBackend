package com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry

import org.ktorm.entity.Entity

interface DiaryEntryEntity: Entity<DiaryEntryEntity> {
    val id:Int
    val mealName: String
    val timestamp: Long
    val date:String
    val weight: Int
    val productId:Int
}