package com.gmail.bogumilmecel2.diary_feature.domain.repository

import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product

interface DiaryRepository {

    suspend fun getDiaryEntries(date:Long):List<DiaryEntry>

    suspend fun getDiaryEntry(id:String): DiaryEntry

    suspend fun getProducts(text:String):List<Product>

    suspend fun getProduct(name:String): Product

    suspend fun removeDiaryEntry(diaryEntry: DiaryEntry):Boolean

    suspend fun editDiaryEntry(diaryEntry: DiaryEntry):Boolean

    suspend fun insertProduct(product: Product):Boolean
}