package com.gmail.bogumilmecel2.user.log.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.log.domain.repository.LogRepository

class InsertLogEntry(
    private val logRepository: LogRepository
) {

    suspend operator fun invoke(userId: Int, logEntry: LogEntry): Resource<Boolean> {
        return logRepository.saveLogEntry(
            userId = userId,
            entry = logEntry
        )
    }
}