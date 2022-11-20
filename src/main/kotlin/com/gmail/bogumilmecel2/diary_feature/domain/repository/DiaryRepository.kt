package com.gmail.bogumilmecel2.diary_feature.domain.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.CaloriesSumResponse
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntryDto
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.Recipe

interface DiaryRepository {
    suspend fun getDiaryEntries(date: String, userId: String): Resource<List<DiaryEntryDto>>
    suspend fun getDiaryEntry(id: String): Resource<DiaryEntry?>
    suspend fun insertDiaryEntry(diaryEntry: DiaryEntry, userId: String): Resource<DiaryEntryDto>
    suspend fun getProducts(text: String): Resource<List<Product>>
    suspend fun getProductHistory(userId: String): Resource<List<Product>>
    suspend fun getProduct(productId: String): Resource<Product?>
    suspend fun removeDiaryEntry(diaryEntryId: String): Resource<Boolean>
    suspend fun editDiaryEntry(diaryEntry: DiaryEntry): Resource<Boolean>
    suspend fun insertProduct(product: Product, userId: String): Resource<Product>
    suspend fun deleteDiaryEntry(diaryEntryId: String, userId: String): Resource<Boolean>
    suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?>
    suspend fun getUserCaloriesSum(date: String, userId: String): Resource<CaloriesSumResponse>
    suspend fun addNewPrice(productId: String, price: Price): Resource<Price>
    suspend fun addNewRecipe(userId: String, recipe: Recipe): Resource<Recipe>
}