package com.gmail.bogumilmecel2.authentication.domain.use_case

import com.gmail.bogumilmecel2.authentication.domain.model.user.User
import com.gmail.bogumilmecel2.authentication.domain.repository.AuthenticationRepository
import com.gmail.bogumilmecel2.authentication.domain.service.HashingService
import com.gmail.bogumilmecel2.common.util.Resource

class RegisterNewUser(
    private val authenticationRepository: AuthenticationRepository,
    private val hashingService: HashingService
) {
    suspend operator fun invoke(
        username: String,
        password:String
    ): Resource<Int> {
        val saltedHash = hashingService.generateSaltedHash(password)

        val user = User(
            username = username,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        return authenticationRepository.registerNewUser(
            user = user
        )
    }
}