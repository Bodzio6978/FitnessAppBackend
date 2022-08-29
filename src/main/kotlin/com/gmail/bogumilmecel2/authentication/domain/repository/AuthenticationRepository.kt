package com.gmail.bogumilmecel2.authentication.domain.repository

import com.gmail.bogumilmecel2.authentication.domain.model.user.User

interface AuthenticationRepository {

    suspend fun getUserByUsername(username:String): User?

    suspend fun registerNewUser(user: User):Boolean
}