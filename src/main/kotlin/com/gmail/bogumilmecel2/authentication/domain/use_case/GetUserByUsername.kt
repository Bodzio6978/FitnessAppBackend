package com.gmail.bogumilmecel2.authentication.domain.use_case

import com.gmail.bogumilmecel2.authentication.domain.model.hash.SaltedHash
import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenClaim
import com.gmail.bogumilmecel2.authentication.domain.model.token.TokenConfig
import com.gmail.bogumilmecel2.authentication.domain.service.HashingService
import com.gmail.bogumilmecel2.authentication.domain.service.TokenService
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository

class GetUserByUsername(
    private val userRepository: UserRepository,
    private val hashingService: HashingService,
    private val tokenService: TokenService
) {

    suspend operator fun invoke(
        username:String,
        password:String,
        tokenConfig: TokenConfig
    ):Resource<String>{
        val resource = userRepository.getUserByUsername(
            username = username
        )
        return when(resource){
            is Resource.Success -> {
                resource.data?.let { user ->
                    val isValidPassword = hashingService.verify(
                        value = password,
                        saltedHash = SaltedHash(
                            hash = user.password,
                            salt = user.salt
                        )
                    )

                    if (!isValidPassword){
                        return Resource.Error(
                            UnauthorizedException()
                        )
                    }

                    val token = tokenService.generate(
                        config = tokenConfig,
                        TokenClaim(
                            name = "userId",
                            value = user.id.toString()
                        )
                    )

                    Resource.Success(token)
                } ?: Resource.Error(
                    error = NoUserFoundException()
                )
            }
            is Resource.Error -> {
                Resource.Error(resource.error!!)
            }
        }
    }
}

class NoUserFoundException() : Exception()
class UnauthorizedException() : Exception()