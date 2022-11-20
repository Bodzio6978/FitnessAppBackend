package com.gmail.bogumilmecel2.user.weight.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.user.weight.domain.repository.WeightRepository

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