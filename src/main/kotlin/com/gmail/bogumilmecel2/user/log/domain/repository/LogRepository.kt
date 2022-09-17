package com.gmail.bogumilmecel2.user.log.domain.repository

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry

interface LogRepository {

    suspend fun saveLogEntry(entry:LogEntry, userId: Int):Resource<Boolean>

    suspend fun getLatestLogEntry(userId:Int):Resource<LogEntry>
}