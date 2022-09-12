package com.gmail.bogumilmecel2.authentication.domain.use_case

data class AuthRoutes(
    val registerNewUser: RegisterNewUser,
    val getUserByUsername: GetUserByUsername
)
