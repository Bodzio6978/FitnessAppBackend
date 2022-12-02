package com.gmail.bogumilmecel2.authentication.domain.use_case

import com.gmail.bogumilmecel2.authentication.domain.model.user.UserDto
import com.gmail.bogumilmecel2.authentication.domain.service.HashingService
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository

class RegisterNewUser(
    private val userRepository: UserRepository,
    private val hashingService: HashingService
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password:String
    ): Resource<Boolean> {
        val saltedHash = hashingService.generateSaltedHash(password)

        val user = UserDto(
            username = username,
            email = email,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        return userRepository.registerNewUser(
            user = user
        )
    }
}