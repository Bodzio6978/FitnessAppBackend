package com.gmail.bogumilmecel2.diary_feature.domain.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product

interface DiaryRepository {

    suspend fun getDiaryEntries(date:String, userId:Int):Resource<List<DiaryEntry>>

    suspend fun getDiaryEntry(id:String): DiaryEntry
    suspend fun insertDiaryEntry(diaryEntry: DiaryEntry, userId: Int):Resource<DiaryEntry>
    suspend fun getProducts(text:String): Resource<List<Product>>
    suspend fun getProductHistory(userId: Int):Resource<List<Product>>
    suspend fun getProduct(productId:Int): Resource<Product>
    suspend fun removeDiaryEntry(diaryEntry: DiaryEntry):Boolean
    suspend fun editDiaryEntry(diaryEntry: DiaryEntry):Boolean
    suspend fun insertProduct(product: Product):Resource<Product>
}