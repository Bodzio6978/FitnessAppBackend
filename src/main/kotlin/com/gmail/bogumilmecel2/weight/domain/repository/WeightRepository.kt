package com.gmail.bogumilmecel2.weight.domain.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntry

interface WeightRepository {
    suspend fun getLatestWeightEntries(userId: String):Resource<List<WeightEntry>>
    suspend fun addWeightEntry(weightEntry: WeightEntry, userId:String): Resource<WeightEntry>
}