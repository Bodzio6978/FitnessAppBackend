package com.gmail.bogumilmecel2.diary_feature.data.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.common.util.extensions.toObjectId
import com.gmail.bogumilmecel2.diary_feature.domain.model.CaloriesSumResponse
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.DiaryEntryDto
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.toDiaryEntry
import com.gmail.bogumilmecel2.diary_feature.domain.model.diary_entry.toDto
import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.ProductDto
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.toDto
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.toProduct
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.RecipeDto
import com.gmail.bogumilmecel2.diary_feature.domain.model.recipe.toDto
import com.gmail.bogumilmecel2.diary_feature.domain.repository.DiaryRepository
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class DiaryRepositoryImp(
    private val diaryCol: CoroutineCollection<DiaryEntryDto>,
    private val productCol: CoroutineCollection<ProductDto>,
    private val recipeCol: CoroutineCollection<RecipeDto>
) : DiaryRepository {

    override suspend fun insertDiaryEntry(diaryEntry: DiaryEntry, userId: String): Resource<DiaryEntryDto> {
        return try {
            val insertedDiaryEntryId = diaryCol.insertOne(diaryEntry.toDto(userId)).insertedId?.asObjectId()
            if (insertedDiaryEntryId?.isObjectId == true) {
                val newDiaryEntry = diaryEntry.toDto(userId).copy(_id = insertedDiaryEntryId.value)
                Resource.Success(newDiaryEntry)
            } else {
                throw NullPointerException()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getDiaryEntries(date: String, userId: String): Resource<List<DiaryEntryDto>> {
        return try {
            val items =
                diaryCol.find(DiaryEntryDto::userId eq userId.toObjectId(), DiaryEntryDto::date eq date).toList()
            Resource.Success(data = items)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getDiaryEntry(id: String): Resource<DiaryEntry?> {
        return try {
            Resource.Success(
                diaryCol.findOne(DiaryEntryDto::_id eq id.toObjectId())?.toDiaryEntry()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getProducts(text: String): Resource<List<Product>> {
        return try {
            Resource.Success(productCol.find(ProductDto::name eq "/.*$text.*/").toList().map {
                it.toProduct()
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getProductHistory(userId: String): Resource<List<Product>> {
        return try {
            val history = mutableListOf<Product>()
            diaryCol.find(DiaryEntryDto::userId eq userId.toObjectId()).limit(20).toList().map {
                it.product.id?.let { id ->
                    val searchResource = getProduct(productId = id)
                    searchResource.data?.let { product ->
                        history.add(product)
                    }
                }
            }
            Resource.Success(history)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getProduct(productId: String): Resource<Product?> {
        return try {
            Resource.Success(productCol.findOne(ProductDto::creatorId eq productId.toObjectId())?.toProduct())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun removeDiaryEntry(diaryEntryId: String): Resource<Boolean> {
        return try {
            Resource.Success(diaryCol.deleteOneById(diaryEntryId).wasAcknowledged())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun editDiaryEntry(diaryEntry: DiaryEntry): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun insertProduct(product: Product, userId: String): Resource<Product> {
        return try {
            productCol.insertOne(product.toDto(userId = userId)).insertedId?.asObjectId()?.value?.let {
                Resource.Success(product.copy(id = it.toString()))
            } ?: Resource.Error(NullPointerException())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun deleteDiaryEntry(diaryEntryId: String, userId: String): Resource<Boolean> {
        return try {
            Resource.Success(
                diaryCol.deleteOne(
                    DiaryEntryDto::_id eq diaryEntryId.toObjectId(),
                    DiaryEntryDto::userId eq userId.toObjectId()
                ).wasAcknowledged()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun searchForProductWithBarcode(barcode: String): Resource<Product?> {
        return try {
            Resource.Success(productCol.findOne(ProductDto::barcode eq barcode)?.toProduct())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getUserCaloriesSum(date: String, userId: String): Resource<CaloriesSumResponse> {
        return try {
            Resource.Success(
                CaloriesSumResponse(caloriesSum = diaryCol.find(
                    DiaryEntryDto::date eq date,
                    DiaryEntryDto::userId eq userId.toObjectId()
                )
                    .toList()
                    .sumOf { it.nutritionValues.calories })
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun addNewPrice(productId: String, price: Price): Resource<Price> {
        return try {
            productCol.findOne(ProductDto::_id eq productId.toObjectId())?.price?.let {
                val newPrice = it.copy(value = (it.value + price.value) / 2.0)
                productCol.updateOneById(productId, setValue(Product::price, newPrice))
                Resource.Success(newPrice)
            } ?: Resource.Error(NullPointerException())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun addNewRecipe(userId: String, recipe: Recipe): Resource<Recipe> {
        return try {
            recipeCol.insertOne(recipe.toDto(userId.toObjectId())).insertedId?.asObjectId()?.toString()?.let {
                Resource.Success(recipe.copy(id = it))
            } ?: Resource.Error(NullPointerException())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}