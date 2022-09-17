package com.gmail.bogumilmecel2.user.user_data.data.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository
import org.ktorm.database.Database

class UserRepositoryImp(
    private val database: Database
): UserRepository {

    override suspend fun saveUserInformation(userInformation: UserInformation, userId: Int): Resource<UserInformation> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNutritionValues(nutritionValues: NutritionValues, userId: Int): Resource<NutritionValues> {
        TODO("Not yet implemented")
    }
}