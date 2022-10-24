package com.gmail.bogumilmecel2.authentication.domain.model.user

import org.ktorm.entity.Entity

interface UserEntity:Entity<UserEntity> {
    val username:String
    val password:String
    val salt:String
    val id:Int
    val nutritionValuesId: Int
    val userInformationId: Int
}