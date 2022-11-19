package com.gmail.bogumilmecel2.weight.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.weight.domain.repository.WeightRepository

class GetLatestWeightEntries(
    private val weightRepository: WeightRepository
) {
    suspend operator fun invoke(
        userId: String
    ): Resource<List<WeightEntry>> {
        return weightRepository.getLatestWeightEntries(
            userId = userId,
        )
    }
}