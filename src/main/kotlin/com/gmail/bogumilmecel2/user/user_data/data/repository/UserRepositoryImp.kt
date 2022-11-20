package com.gmail.bogumilmecel2.user.user_data.data.repository

import com.gmail.bogumilmecel2.authentication.domain.model.user.User
import com.gmail.bogumilmecel2.authentication.domain.model.user.UserDto
import com.gmail.bogumilmecel2.authentication.domain.model.user.toDto
import com.gmail.bogumilmecel2.authentication.domain.model.user.toUser
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.common.util.extensions.toObjectId
import com.gmail.bogumilmecel2.diary_feature.domain.model.nutrition_values.NutritionValues
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class UserRepositoryImp(
    private val userCol: CoroutineCollection<UserDto>
) : UserRepository {

    override suspend fun saveUserInformation(userInformation: UserInformation, userId: String): Resource<Boolean> {
        return try {
            Resource.Success(userCol.updateOneById(userId.toObjectId(), setValue(User::userInformation, userInformation)).wasAcknowledged())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun saveUserNutritionValues(
        nutritionValues: NutritionValues,
        userId: String
    ): Resource<Boolean> {
        return try {
            println("$userId $nutritionValues")
            Resource.Success(userCol.updateOneById(userId.toObjectId(), setValue(User::nutritionValues, nutritionValues)).wasAcknowledged())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getUserNutritionValues(userId: String): Resource<NutritionValues?> {
        return try {
            Resource.Success(userCol.findOneById(userId)?.nutritionValues)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getUserInformation(userId: String): Resource<UserInformation?> {
        return try {
            Resource.Success(userCol.findOneById(userId)?.userInformation)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun saveLogEntry(entry: LogEntry, userId: String): Resource<LogEntry> {
        return try {
            userCol.updateOneById(userId.toObjectId(), setValue(UserDto::latestLogEntry, entry))
            Resource.Success(
                data = LogEntry(
                    streak = entry.streak,
                    timestamp = entry.timestamp
                )
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }

    }

    override suspend fun getLatestLogEntry(userId: String): Resource<LogEntry?> {
        return try {
            Resource.Success(data = userCol.findOneById(userId.toObjectId())?.latestLogEntry)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun getUserByUsername(username: String): Resource<User?> {
        return try {
            Resource.Success(userCol.findOne(User::username eq username)?.toUser())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override suspend fun registerNewUser(user: User): Resource<Boolean> {
        return try {
            Resource.Success(userCol.insertOne(user.toDto()).wasAcknowledged())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}