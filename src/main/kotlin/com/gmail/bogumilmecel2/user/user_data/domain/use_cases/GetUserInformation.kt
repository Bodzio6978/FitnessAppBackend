package com.gmail.bogumilmecel2.user.user_data.domain.use_cases

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.user_data.domain.model.UserInformation
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository

class GetUserInformation(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Resource<UserInformation?> {
        return userRepository.getUserInformation(userId)
    }
}