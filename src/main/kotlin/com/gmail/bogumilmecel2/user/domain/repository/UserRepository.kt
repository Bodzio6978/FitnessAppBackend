package com.gmail.bogumilmecel2.user.domain.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.domain.model.UserInformation

interface UserRepository {

    suspend fun saveUserInformation(
        userInformation:UserInformation,
        userId:Int
    ):Resource<UserInformation>

    suspend fun saveNutritionValues(
        nutritionValues: NutritionValues,
        userId:Int
    ):Resource<NutritionValues>
}