package com.gmail.bogumilmecel2.diary_feature.domain.product

import com.gmail.bogumilmecel2.diary_feature.domain.model.Price
import org.ktorm.entity.Entity

interface ProductEntity : Entity<ProductEntity> {
    val id:Int
    val name: String
    val containerWeight: Int
    val position: Int
    val unit: String
    val nutritionValuesId: Int
    val barcode: String?
    val price:Price
}