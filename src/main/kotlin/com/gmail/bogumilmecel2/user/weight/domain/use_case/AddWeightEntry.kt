package com.gmail.bogumilmecel2.user.weight.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.user.weight.domain.repository.WeightRepository

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