package com.gmail.bogumilmecel2.security.domain.model.hash

data class SaltedHash(
    val hash:String,
    val salt:String
)
