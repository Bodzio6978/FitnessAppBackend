package com.gmail.bogumilmecel2.user.user_data.data.repository

import com.gmail.bogumilmecel2.authentication.data.table.UserTable
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.diary_feature.data.table.NutritionValuesTable
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.user_data.data.table.UserInformationTable
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*

class UserRepositoryImp(
    private val database: Database
) : UserRepository {

    override suspend fun saveUserInformation(userInformation: UserInformation, userId: Int): Resource<UserInformation> {
        return try {
            val insertedUserInformationId = database.insertAndGenerateKey(UserInformationTable) {
                set(it.activityInADay, userInformation.activityInADay)
                set(it.typeOfWork, userInformation.typeOfWork)
                set(it.workoutInAWeek, userInformation.workoutInAWeek)
                set(it.gender, userInformation.gender)
                set(it.height, userInformation.height)
                set(it.currentWeight, userInformation.currentWeight)
                set(it.wantedWeight, userInformation.wantedWeight)
                set(it.age, userInformation.age)
            } as Int
            database.update(UserTable) {
                set(it.userInformationId, insertedUserInformationId)
                where {
                    it.id eq userId
                }
            }
            Resource.Success(userInformation.copy(id = insertedUserInformationId))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun saveUserNutritionValues(
        nutritionValues: NutritionValues,
        userId: Int
    ): Resource<NutritionValues> {
        return try {
            val insertedNutritionValuesId = database.insertAndGenerateKey(NutritionValuesTable) {
                set(it.calories, nutritionValues.calories)
                set(it.carbohydrates, nutritionValues.carbohydrates)
                set(it.protein, nutritionValues.protein)
                set(it.fat, nutritionValues.fat)
            } as Int
            database.update(UserTable) {
                set(it.nutritionValuesId, insertedNutritionValuesId)
                where {
                    it.id eq userId
                }
            }
            Resource.Success(nutritionValues.copy(id = insertedNutritionValuesId))
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getUserNutritionValues(userId: Int): Resource<NutritionValues?> {
        return try {
            val userNutritionValues = database.from(NutritionValuesTable)
                .innerJoin(UserTable, on = UserTable.nutritionValuesId eq NutritionValuesTable.id)
                .select()
                .limit(1)
                .map {
                    NutritionValues(
                        id = it[NutritionValuesTable.id] ?: -1,
                        calories = it[NutritionValuesTable.calories] ?: 0,
                        carbohydrates = it[NutritionValuesTable.carbohydrates] ?: 0.0,
                        protein = it[NutritionValuesTable.protein] ?: 0.0,
                        fat = it[NutritionValuesTable.fat] ?: 0.0
                    )
                }.firstOrNull()
            Resource.Success(userNutritionValues)
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

    override suspend fun getUserInformation(userId: Int): Resource<UserInformation?> {
        return try {
            val item = database.from(UserTable)
                .innerJoin(UserTable, on = UserTable.userInformationId eq UserInformationTable.id)
                .select()
                .limit(1)
                .map {
                    UserInformation(
                        id = it[UserInformationTable.id] ?: -1,
                        activityInADay = it[UserInformationTable.activityInADay] ?: 0,
                        typeOfWork = it[UserInformationTable.typeOfWork] ?: 0,
                        workoutInAWeek = it[UserInformationTable.workoutInAWeek] ?: 0,
                        gender = it[UserInformationTable.gender] ?: 0,
                        height = it[UserInformationTable.height] ?: 0.0,
                        currentWeight = it[UserInformationTable.currentWeight] ?: 0.0,
                        wantedWeight = it[UserInformationTable.wantedWeight] ?: 0.0,
                        age = it[UserInformationTable.age] ?: 0,
                    )
                }.firstOrNull()
            Resource.Success(item)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}