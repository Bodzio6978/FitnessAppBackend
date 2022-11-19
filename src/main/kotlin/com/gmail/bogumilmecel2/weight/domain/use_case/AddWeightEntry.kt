package com.gmail.bogumilmecel2.weight.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.weight.domain.repository.WeightRepository

class AddWeightEntry(
    private val weightRepository: WeightRepository
) {

    suspend operator fun invoke(
        weightEntry: WeightEntry,
        userId: String
    ):Resource<WeightEntry>{
        return weightRepository.addWeightEntry(
            userId = userId,
            weightEntry = weightEntry
        )
    }
}