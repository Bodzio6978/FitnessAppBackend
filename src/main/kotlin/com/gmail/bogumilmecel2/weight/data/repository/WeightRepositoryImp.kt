package com.gmail.bogumilmecel2.weight.data.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.weight.data.table.WeightTable
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.weight.domain.repository.WeightRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*

class WeightRepositoryImp(
    private val database: Database
) : WeightRepository {
    override suspend fun getLatestWeightEntries(userId: Int): Resource<List<WeightEntry>> {
        return try {
            val entries = database.from(WeightTable)
                .select()
                .where { WeightTable.userId eq userId }
                .orderBy(WeightTable.timestamp.desc())
                .limit(14)
                .map {
                    WeightEntry(
                        id = it[WeightTable.id] ?: -1,
                        value = it[WeightTable.value] ?: 0.0,
                        timestamp = it[WeightTable.timestamp] ?: System.currentTimeMillis(),
                    )
                }
            Resource.Success(data = entries)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry, userId: Int): Resource<WeightEntry> {
        return try {
            val key = database.insertAndGenerateKey(WeightTable){
                set(it.value, weightEntry.value)
                set(it.timestamp, weightEntry.timestamp)
                set(it.userId, userId)
            } as Int
            Resource.Success(
                data = WeightEntry(
                    id = key,
                    value = weightEntry.value,
                    timestamp = weightEntry.timestamp
                )
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }

    }
}