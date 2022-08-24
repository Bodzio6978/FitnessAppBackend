package com.gmail.bogumilmecel2.diary_feature.data.repository

import com.gmail.bogumilmecel2.diary_feature.domain.model.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository

class DiaryRepositoryImp:DiaryRepository {

    override suspend fun getDiaryEntries(date: Long): List<DiaryEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiaryEntry(id: String): DiaryEntry {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(text: String): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getProduct(name: String): Product {
        TODO("Not yet implemented")
    }

    override suspend fun removeDiaryEntry(diaryEntry: DiaryEntry): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun editDiaryEntry(diaryEntry: DiaryEntry): Boolean {
        TODO("Not yet implemented")
    }
}