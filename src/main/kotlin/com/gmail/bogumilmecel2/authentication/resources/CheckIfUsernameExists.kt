package com.gmail.bogumilmecel2.authentication.resources

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/username/")
class CheckIfUsernameExistsResource(
    val username:String = ""
)