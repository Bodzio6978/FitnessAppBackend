package com.gmail.bogumilmecel2.user.log.domain.model

import org.ktorm.entity.Entity

interface LogEntryEntity: Entity<LogEntryEntity> {
    val id:Int
    val userId:Int
    val timestamp:Long
    val streak:Int
}