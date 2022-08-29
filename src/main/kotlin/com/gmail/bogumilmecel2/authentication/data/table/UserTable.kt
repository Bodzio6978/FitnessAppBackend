package com.gmail.bogumilmecel2.authentication.data.table

import com.gmail.bogumilmecel2.authentication.domain.model.user.UserEntity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

object UserTable:Table<UserEntity>(tableName = "user") {
    val id = int("id").primaryKey().bindTo { it.id }
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
    val salt = varchar("salt").bindTo { it.salt }
}