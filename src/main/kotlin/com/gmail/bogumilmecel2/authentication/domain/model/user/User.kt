package com.gmail.bogumilmecel2.authentication.domain.model.user

data class User(
    val id:Int = 0,
    val username:String = "",
    val password:String = "",
    val salt:String = ""
)
