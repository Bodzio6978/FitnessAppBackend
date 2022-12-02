package com.gmail.bogumilmecel2.authentication.domain.model.user

import org.bson.types.ObjectId

fun User.toDto(): UserDto = UserDto(
     _id = ObjectId(),
     username = username,
     nutritionValues = nutritionValues,
     userInformation = userInformation,
     latestLogEntry = latestLogEntry,
)

fun UserDto.toUser():User = User(
     username = username,
     nutritionValues = nutritionValues,
     userInformation = userInformation,
     latestLogEntry = latestLogEntry,
     weightEntries = weightEntries
)
