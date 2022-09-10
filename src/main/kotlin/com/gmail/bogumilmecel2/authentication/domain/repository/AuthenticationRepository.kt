package com.gmail.bogumilmecel2.authentication.domain.repository

import com.gmail.bogumilmecel2.authentication.domain.model.user.User
import com.gmail.bogumilmecel2.common.util.Resource

interface AuthenticationRepository {

    suspend fun getUserByUsername(username:String): Resource<User?>

    suspend fun registerNewUser(user: User):Resource<Int>
}