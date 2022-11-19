package com.gmail.bogumilmecel2.weight.data.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.common.util.extensions.toObjectId
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntryDto
import com.gmail.bogumilmecel2.weight.domain.model.toDto
import com.gmail.bogumilmecel2.weight.domain.model.toWeightEntry
import com.gmail.bogumilmecel2.weight.domain.repository.WeightRepository
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq

class WeightRepositoryImp(
    private val weightCol: CoroutineCollection<WeightEntryDto>
) : WeightRepository {
    override suspend fun getLatestWeightEntries(userId: String): Resource<List<WeightEntry>> {
        return try {
            Resource.Success(data = weightCol.find(WeightEntryDto::userId eq userId.toObjectId())
                .descendingSort(WeightEntryDto::timestamp).limit(14).toList()
                .map {
                    it.toWeightEntry()
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry, userId: String): Resource<WeightEntry> {
        return try {
            weightCol.insertOne(weightEntry.toDto(userId)).insertedId?.asObjectId()?.let {
                Resource.Success(
                    data = weightEntry.copy(
                        id = it.toString()
                    )
                )
            } ?: Resource.Error(NullPointerException())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }

    }
}