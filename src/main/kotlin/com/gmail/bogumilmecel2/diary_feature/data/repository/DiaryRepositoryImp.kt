package com.gmail.bogumilmecel2.diary_feature.data.repository

import com.gmail.bogumilmecel2.common.exception.NoDatabaseEntryException
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.common.util.extensions.formatToString
import com.gmail.bogumilmecel2.common.util.extensions.mapProduct
import com.gmail.bogumilmecel2.diary_feature.data.table.diary_entry.DiaryEntriesTable
import com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values.NutritionValuesTable
import com.gmail.bogumilmecel2.diary_feature.data.table.price.PriceTable
import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.*

class DiaryRepositoryImp(
    private val database: Database
) : DiaryRepository {

    override suspend fun insertDiaryEntry(diaryEntry: DiaryEntry, userId: Int):Resource<DiaryEntry> {
        return try {
            val diaryEntryId = database.insertAndGenerateKey(DiaryEntriesTable){
                set(it.date, diaryEntry.date)
                set(it.mealName, diaryEntry.mealName)
                set(it.weight, diaryEntry.weight)
                set(it.productId, diaryEntry.product.id)
                set(it.timestamp, diaryEntry.timeStamp)
                set(it.userId, userId)
            } as Int
            Resource.Success(
                data = diaryEntry.copy(
                    id = diaryEntryId
                )
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }

    }

    override suspend fun getDiaryEntries(date: String, userId: Int): Resource<List<DiaryEntry>> {
        return try {
            val query = database.from(DiaryEntriesTable)
                .innerJoin(ProductTable, on = ProductTable.id eq DiaryEntriesTable.productId)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.nutritionValuesId)
                .innerJoin(PriceTable, on = PriceTable.id eq ProductTable.priceId)
                .select()
                .where {
                    (DiaryEntriesTable.date eq date) and (DiaryEntriesTable.userId eq userId)
                }.map {
                    DiaryEntry(
                        id = it[DiaryEntriesTable.id] ?: -1,
                        timeStamp = it[DiaryEntriesTable.timestamp] ?: System.currentTimeMillis(),
                        date = it[DiaryEntriesTable.date] ?: Date(System.currentTimeMillis()).formatToString(),
                        weight = it[DiaryEntriesTable.weight] ?: 0,
                        product = it.mapProduct(),
                        mealName = it[DiaryEntriesTable.mealName] ?: "Breakfast"
                    )
                }
            Resource.Success(data = query)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getDiaryEntry(id: String): DiaryEntry {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(text: String): Resource<List<Product>> {
        return try {
            val query = database.from(ProductTable).innerJoin(PriceTable, on = PriceTable.id eq ProductTable.priceId)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.priceId)
                .select()
                .map {
                    it.mapProduct()
                }
            Resource.Success(data = query)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }

    }

    override suspend fun getProduct(productId: Int): Resource<Product> {
        return try {
            val query = database.from(ProductTable).select().where {
                ProductTable.id eq productId
            }.map {
                it.mapProduct()
            }
            if (query.isNotEmpty()) {
                Resource.Success(data = query[0])
            } else {
                throw NoDatabaseEntryException()
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun removeDiaryEntry(diaryEntry: DiaryEntry): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun editDiaryEntry(diaryEntry: DiaryEntry): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun insertProduct(product: Product): Resource<Product> {
        return try {
            val insertedPriceId = database.insertAndGenerateKey(PriceTable){
                set(it.value, product.price.value)
                set(it.forHowMuch, product.price.forHowMuch)
            } as Int

            val insertedNutritionValuesId = database.insertAndGenerateKey(NutritionValuesTable){
                set(it.calories, product.nutritionValues.calories)
                set(it.carbohydrates, product.nutritionValues.carbohydrates)
                set(it.protein, product.nutritionValues.protein)
                set(it.fat, product.nutritionValues.fat)
            } as Int

            val insertedProductId = database.insertAndGenerateKey(ProductTable) {
                set(it.name, product.name)
                set(it.containerWeight, product.containerWeight)
                set(it.position, product.position)
                set(it.unit, product.name)
                set(it.nutritionValuesId, 1)
                set(it.barcode, product.barcode)
                set(it.priceId, 1)
            } as Int

            Resource.Success(
                data = product.copy(
                    id = insertedProductId,
                    price = product.price.copy(
                        id = insertedPriceId
                    ),
                    nutritionValues = product.nutritionValues.copy(
                        id = insertedNutritionValuesId
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getProductHistory(userId: Int): Resource<List<Product>> {
        return try {
            val query = database.from(DiaryEntriesTable)
                .innerJoin(ProductTable, on = ProductTable.id eq DiaryEntriesTable.productId)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.nutritionValuesId)
                .innerJoin(PriceTable, on = PriceTable.id eq ProductTable.priceId)
                .select()
                .limit(20)
                .orderBy(DiaryEntriesTable.timestamp.desc())
                .groupBy(ProductTable.name)
                .where {
                    DiaryEntriesTable.userId eq userId
                }.map {
                    it.mapProduct()
                }
            Resource.Success(query)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun deleteDiaryEntry(diaryEntryId: Int, userId: Int): Resource<Boolean> {
        return try {
            database.delete(DiaryEntriesTable){
                (it.id eq diaryEntryId) and (it.userId eq userId)
            }
            Resource.Success(true)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}