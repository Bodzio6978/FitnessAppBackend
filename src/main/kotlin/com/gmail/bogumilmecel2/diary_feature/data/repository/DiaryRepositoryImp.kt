package com.gmail.bogumilmecel2.diary_feature.data.repository

import com.gmail.bogumilmecel2.common.exception.NoDatabaseEntryException
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.common.util.extensions.mapProduct
import com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values.NutritionValuesTable
import com.gmail.bogumilmecel2.diary_feature.data.table.price.PriceTable
import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*

class DiaryRepositoryImp(
    private val database: Database
) : DiaryRepository {

    override suspend fun getDiaryEntries(date: Long): List<DiaryEntry> {
        TODO("Not yet implemented")
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
            Resource.Error(e)
        }
    }
}