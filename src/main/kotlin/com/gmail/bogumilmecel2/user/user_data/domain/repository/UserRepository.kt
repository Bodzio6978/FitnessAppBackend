package com.gmail.bogumilmecel2.user.user_data.domain.repository

import com.gmail.bogumilmecel2.authentication.domain.model.user.User
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation

interface UserRepository {
    suspend fun saveUserNutritionValues(nutritionValues: NutritionValues, userId:String): Resource<Boolean>
    suspend fun getUserNutritionValues(userId: String):Resource<NutritionValues?>
    suspend fun getUserInformation(userId: String):Resource<UserInformation?>
    suspend fun saveUserInformation(userInformation: UserInformation, userId: String): Resource<Boolean>
    suspend fun saveLogEntry(entry: LogEntry, userId: String):Resource<LogEntry>
    suspend fun getLatestLogEntry(userId:String):Resource<LogEntry?>
    suspend fun getUserByUsername(username: String): Resource<User?>
    suspend fun registerNewUser(user: User): Resource<Boolean>

}