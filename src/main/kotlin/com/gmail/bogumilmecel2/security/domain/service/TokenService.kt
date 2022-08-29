package com.gmail.bogumilmecel2.security.domain.service

import com.gmail.bogumilmecel2.security.domain.model.token.TokenClaim
import com.gmail.bogumilmecel2.security.domain.model.token.TokenConfig

interface TokenService {

    fun generate(
        config: TokenConfig,
        vararg claims: TokenClaim
    ):String


}