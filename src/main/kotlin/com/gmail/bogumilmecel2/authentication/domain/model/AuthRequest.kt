package com.gmail.bogumilmecel2.authentication.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val username:String,
    val password:String
)
