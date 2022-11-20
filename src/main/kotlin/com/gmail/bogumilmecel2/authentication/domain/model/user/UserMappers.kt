package com.gmail.bogumilmecel2.authentication.domain.model.user

import com.gmail.bogumilmecel2.common.util.extensions.toObjectId

fun User.toDto(): UserDto = UserDto(
     _id = id.toObjectId(),
     username = username,
     password = password,
     salt = salt,
     nutritionValues = nutritionValues,
     userInformation = userInformation,
     latestLogEntry = latestLogEntry,
)

fun UserDto.toUser():User = User(
     id = _id.toString(),
     username = username,
     password = password,
     salt = salt,
     nutritionValues = nutritionValues,
     userInformation = userInformation,
     latestLogEntry = latestLogEntry,
)
