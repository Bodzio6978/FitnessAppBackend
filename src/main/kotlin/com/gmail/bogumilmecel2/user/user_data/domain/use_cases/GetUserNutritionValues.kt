package com.gmail.bogumilmecel2.user.user_data.domain.use_cases

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository

class GetUserNutritionValues(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int):Resource<NutritionValues?>{
        return userRepository.getUserNutritionValues(userId = userId)
    }
}