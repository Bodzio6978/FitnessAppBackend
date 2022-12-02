package com.gmail.bogumilmecel2.user.weight.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository
import com.gmail.bogumilmecel2.user.weight.domain.model.WeightEntry

class GetLatestWeightEntries(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: String
    ): Resource<List<WeightEntry>> {
        return userRepository.getLatestWeightEntries(
            userId = userId,
        )
    }
}