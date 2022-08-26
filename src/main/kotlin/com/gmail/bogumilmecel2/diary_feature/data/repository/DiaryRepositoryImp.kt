package com.gmail.bogumilmecel2.diary_feature.data.repository

import com.gmail.bogumilmecel2.diary_feature.data.table.nutrition_values.NutritionValuesTable
import com.gmail.bogumilmecel2.diary_feature.data.table.price.PriceTable
import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
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

    override suspend fun getProducts(text: String): List<Product> {
        return database.from(ProductTable)
            .innerJoin(PriceTable, on = PriceTable.id eq ProductTable.priceId)
            .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.priceId)
            .select()
            .map {
                Product(
                    id = it[ProductTable.id] ?: 0,
                    name = it[ProductTable.name] ?: "",
                    containerWeight = it[ProductTable.containerWeight] ?: 0,
                    position = it[ProductTable.position] ?: 0,
                    unit = it[ProductTable.unit] ?: "",
                    price = Price(
                        id = it[PriceTable.id] ?: 0,
                        value = it[PriceTable.value] ?: 0.0,
                        forHowMuch = it[PriceTable.forHowMuch] ?: 0
                    ),
                    nutritionValues = NutritionValues(
                        id = it[NutritionValuesTable.id] ?: 0,
                        calories = it[NutritionValuesTable.calories] ?: 0,
                        carbohydrates = it[NutritionValuesTable.carbohydrates] ?: 0.0,
                        protein = it[NutritionValuesTable.protein] ?: 0.0,
                        fat = it[NutritionValuesTable.fat] ?: 0.0
                    ),
                    barcode = it[ProductTable.barcode]
                )
            }
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

    override suspend fun insertProduct(product: Product): Boolean {
        return try {
            database.insertAndGenerateKey(ProductTable){
                set(it.name,product.name)
                set(it.containerWeight,product.containerWeight)
                set(it.position,product.position)
                set(it.unit,product.name)
                set(it.nutritionValuesId,1)
                set(it.barcode,product.barcode)
                set(it.priceId,1)
            }
            true
        }catch (e:Exception){
            false
        }
    }
}