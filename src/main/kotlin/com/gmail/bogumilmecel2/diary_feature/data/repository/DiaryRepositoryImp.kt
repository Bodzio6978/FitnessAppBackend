package com.gmail.bogumilmecel2.diary_feature.data.repository

import com.gmail.bogumilmecel2.common.exception.NoDatabaseEntryException
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.common.util.extensions.calculateCalories
import com.gmail.bogumilmecel2.common.util.extensions.formatToString
import com.gmail.bogumilmecel2.common.util.extensions.mapFirstPrice
import com.gmail.bogumilmecel2.common.util.extensions.mapProduct
import com.gmail.bogumilmecel2.diary_feature.data.table.*
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.util.*

class DiaryRepositoryImp(
    private val database: Database
) : DiaryRepository {

    override suspend fun insertDiaryEntry(diaryEntry: DiaryEntry, userId: Int): Resource<DiaryEntry> {
        return try {
            val diaryEntryId = database.insertAndGenerateKey(DiaryEntriesTable) {
                set(it.date, diaryEntry.date)
                set(it.mealName, diaryEntry.mealName)
                set(it.weight, diaryEntry.weight)
                set(it.productId, diaryEntry.product.id)
                set(it.timestamp, diaryEntry.timestamp)
                set(it.userId, userId)
            } as Int
            Resource.Success(
                data = diaryEntry.copy(
                    id = diaryEntryId
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }

    }

    override suspend fun getDiaryEntries(date: String, userId: Int): Resource<List<DiaryEntry>> {
        return try {
            val query = database.from(DiaryEntriesTable)
                .innerJoin(ProductTable, on = ProductTable.id eq DiaryEntriesTable.productId)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.nutritionValuesId)
                .select()
                .where {
                    (DiaryEntriesTable.date eq date) and (DiaryEntriesTable.userId eq userId)
                }.map {
                    val productId = it[ProductTable.id] ?: -1
                    val price = it.mapFirstPrice(
                        productId = productId,
                        database = database
                    )
                    DiaryEntry(
                        id = it[DiaryEntriesTable.id] ?: -1,
                        timestamp = it[DiaryEntriesTable.timestamp] ?: System.currentTimeMillis(),
                        date = it[DiaryEntriesTable.date] ?: Date(System.currentTimeMillis()).formatToString(),
                        weight = it[DiaryEntriesTable.weight] ?: 0,
                        product = it.mapProduct(price),
                        mealName = it[DiaryEntriesTable.mealName] ?: "Breakfast"
                    )
                }
            Resource.Success(data = query)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getDiaryEntry(id: String): DiaryEntry {
        TODO("Not yet implemented")
    }

    override suspend fun getProducts(text: String): Resource<List<Product>> {
        return try {
            val query = database.from(ProductTable)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.nutritionValuesId)
                .select()
                .where {
                    ProductTable.name like "%$text%"
                }
                .map {
                    val productId = it[ProductTable.id] ?: -1
                    val price = it.mapFirstPrice(
                        productId = productId,
                        database = database
                    )
                    it.mapProduct(price)
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
                val currentProductId = it[ProductTable.id] ?: -1
                val price = it.mapFirstPrice(
                    productId = currentProductId,
                    database = database
                )
                it.mapProduct(price)
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
            val insertedNutritionValuesId = database.insertAndGenerateKey(NutritionValuesTable) {
                set(it.calories, product.nutritionValues.calories)
                set(it.carbohydrates, product.nutritionValues.carbohydrates)
                set(it.protein, product.nutritionValues.protein)
                set(it.fat, product.nutritionValues.fat)
            } as Int

            val insertedProductId = database.insertAndGenerateKey(ProductTable) {
                set(it.name, product.name)
                set(it.containerWeight, product.containerWeight)
                set(it.position, product.position)
                set(it.unit, product.unit)
                set(it.nutritionValuesId, insertedNutritionValuesId)
                set(it.barcode, product.barcode)
            } as Int

            Resource.Success(
                data = product.copy(
                    id = insertedProductId,
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
                .select()
                .limit(20)
                .orderBy(DiaryEntriesTable.timestamp.desc())
                .groupBy(ProductTable.name)
                .where {
                    DiaryEntriesTable.userId eq userId
                }.map {
                    val productId = it[ProductTable.id] ?: -1
                    val price = it.mapFirstPrice(
                        productId = productId,
                        database = database
                    )
                    it.mapProduct(price)
                }
            Resource.Success(query)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun deleteDiaryEntry(diaryEntryId: Int, userId: Int): Resource<Boolean> {
        return try {
            database.delete(DiaryEntriesTable) {
                (it.id eq diaryEntryId) and (it.userId eq userId)
            }
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?> {
        return try {
            val query = database.from(ProductTable)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.nutritionValuesId)
                .select()
                .where {
                    ProductTable.barcode eq barcode
                }
                .limit(1)
                .map {
                    val productId = it[ProductTable.id] ?: -1
                    val price = it.mapFirstPrice(
                        productId = productId,
                        database = database
                    )
                    it.mapProduct(price)
                }
            if (query.isNotEmpty()) {
                Resource.Success(data = query[0])
            } else {
                Resource.Success(data = null)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getUserCaloriesSum(date: String, userId: Int): Resource<List<Int>> {
        return try {
            val sum = database.from(DiaryEntriesTable)
                .innerJoin(ProductTable, on = ProductTable.id eq DiaryEntriesTable.productId)
                .innerJoin(NutritionValuesTable, on = NutritionValuesTable.id eq ProductTable.nutritionValuesId)
                .select()
                .where {
                    (DiaryEntriesTable.userId eq userId) and (DiaryEntriesTable.date eq date)
                }.map {
                    DiaryEntry(
                        id = it[DiaryEntriesTable.id] ?: -1,
                        timestamp = it[DiaryEntriesTable.timestamp] ?: System.currentTimeMillis(),
                        date = it[DiaryEntriesTable.date] ?: Date(System.currentTimeMillis()).formatToString(),
                        weight = it[DiaryEntriesTable.weight] ?: 0,
                        product = it.mapProduct(),
                        mealName = it[DiaryEntriesTable.mealName] ?: "Breakfast"
                    ).calculateCalories()
                }
            return Resource.Success(sum)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun addNewPrice(productId: Int, price: Price): Resource<Price> {
        return try {
            val productPrice = database.from(PriceTable)
                .select()
                .where {
                    (PriceTable.productId eq productId) and (PriceTable.currency eq price.currency)
                }.map {
                    Price(
                        id = it[PriceTable.id] ?: -1,
                        value = it[PriceTable.value] ?: 0.0,
                        currency = it[PriceTable.currency] ?: ""
                    )
                }
            if (productPrice.isEmpty()) {
                val newPriceId = database.insertAndGenerateKey(PriceTable) {
                    set(it.value, price.value)
                    set(it.currency, price.currency)
                    set(it.productId, productId)
                } as Int
                Resource.Success(
                    data = price.copy(
                        id = newPriceId
                    )
                )
            } else {
                val priceWithSelectedCurrency = productPrice[0]
                val newPrice = priceWithSelectedCurrency.copy(
                    value = (priceWithSelectedCurrency.value + price.value) / 2
                )
                database.update(PriceTable) {
                    set(it.id, newPrice.id)
                    set(it.value, newPrice.value)
                    set(it.currency, price.currency)
                    set(it.productId, productId)
                }
                Resource.Success(newPrice)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun addNewRecipe(userId: Int, recipe: Recipe): Resource<Recipe> {
        return try {
            val ingredients = mutableListOf<Ingredient>()
            val recipeId = database.insertAndGenerateKey(RecipeTable) {
                set(it.name, recipe.name)
                set(it.imageUrl, recipe.imageUrl)
                set(it.timeNeeded, recipe.timeNeeded)
                set(it.difficulty, recipe.difficulty)
                set(it.servings, recipe.servings)
                set(it.timestamp, recipe.timestamp)
                set(it.userId, userId)
            } as Int
            recipe.ingredients.forEach { ingredient ->
                val ingredientId = database.insertAndGenerateKey(IngredientTable) {
                    set(it.weight, ingredient.weight)
                    set(it.productId, ingredient.product.id)
                    set(it.recipeId, recipeId)
                } as Int
                ingredients.add(
                    ingredient.copy(
                        id = ingredientId
                    )
                )
            }
            Resource.Success(
                data = recipe.copy(
                    id = recipeId,
                    ingredients = ingredients
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}