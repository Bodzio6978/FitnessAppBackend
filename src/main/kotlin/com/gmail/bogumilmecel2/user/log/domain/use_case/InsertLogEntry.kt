package com.gmail.bogumilmecel2.user.log.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.user_data.domain.repository.UserRepository
import org.apache.commons.lang3.time.DateUtils
import java.util.*

class InsertLogEntry(
    private val userRepository: UserRepository,
    private val getLatestLogEntry: GetLatestLogEntry
) {

    suspend operator fun invoke(userId: String, timestamp: Long): Resource<LogEntry> {
        val newLogEntry:LogEntry = when(val latestLogEntryResource = getLatestLogEntry(userId = userId)){
            is Resource.Success -> {
                latestLogEntryResource.data?.let { logEntry ->
                    if (DateUtils.isSameDay(Date(timestamp), Date(logEntry.timestamp))){
                        return Resource.Success(data = logEntry)
                    }
                    if (DateUtils.isSameDay(Date(timestamp-DateUtils.MILLIS_PER_DAY), Date(logEntry.timestamp))){
                        LogEntry(
                            timestamp = System.currentTimeMillis(),
                            streak = logEntry.streak + 1
                        )
                    }else{
                        LogEntry()
                    }
                } ?: LogEntry()
            }
            is Resource.Error -> {
                LogEntry()
            }
        }

        return userRepository.saveLogEntry(
            userId = userId,
            entry = newLogEntry
        )
    }
}