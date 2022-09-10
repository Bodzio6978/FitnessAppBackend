package com.gmail.bogumilmecel2.authentication.data.repository

import com.gmail.bogumilmecel2.authentication.data.table.UserTable
import com.gmail.bogumilmecel2.authentication.domain.model.user.User
import com.gmail.bogumilmecel2.authentication.domain.repository.AuthenticationRepository
import com.gmail.bogumilmecel2.common.util.Resource
import org.ktorm.database.Database
import org.ktorm.dsl.*

class AuthenticationRepositoryImp(
    private val database: Database
) : AuthenticationRepository {


    override suspend fun getUserByUsername(username: String): Resource<User?> {
        return try {
            val userList = database.from(UserTable).select().where {
                UserTable.username eq username
            }.limit(1).map {
                User(
                    id = it[UserTable.id] ?: 0,
                    username = it[UserTable.username] ?: "",
                    password = it[UserTable.password] ?: "",
                    salt = it[UserTable.salt] ?: "",
                )
            }
            Resource.Success(userList[0])
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun registerNewUser(user: User): Resource<Int> {
        return try {
            val generatedKey = database.insertAndGenerateKey(UserTable) {
                set(it.username, user.username)
                set(it.password, user.password)
                set(it.salt, user.salt)
            } as Int
            Resource.Success(generatedKey)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}