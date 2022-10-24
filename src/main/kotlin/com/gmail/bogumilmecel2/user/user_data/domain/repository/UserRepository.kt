package com.gmail.bogumilmecel2.user.user_data.domain.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation

interface UserRepository {
    suspend fun saveUserNutritionValues(nutritionValues: NutritionValues, userId:Int):Resource<NutritionValues>
    suspend fun getUserNutritionValues(userId: Int):Resource<NutritionValues?>
    suspend fun getUserInformation(userId: Int):Resource<UserInformation?>
    suspend fun saveUserInformation(userInformation: UserInformation, userId: Int): Resource<UserInformation>
}