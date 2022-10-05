package com.gmail.bogumilmecel2.user.log.domain.use_case

import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.log.domain.repository.LogRepository
import org.apache.commons.lang3.time.DateUtils
import java.util.*

class InsertLogEntry(
    private val logRepository: LogRepository,
    private val getLatestLogEntry: GetLatestLogEntry
) {

    suspend operator fun invoke(userId: Int, timestamp: Long): Resource<LogEntry> {
        val newLogEntry:LogEntry = when(val latestLogEntryResource = getLatestLogEntry(userId = userId)){
            is Resource.Success -> {
                latestLogEntryResource.data?.let { logEntry ->
                    if (DateUtils.isSameDay(Date(timestamp), Date(logEntry.timestamp))){
                        println("today")
                        return Resource.Success(data = logEntry)
                    }
                    if (DateUtils.isSameDay(Date(timestamp-DateUtils.MILLIS_PER_DAY), Date(logEntry.timestamp))){
                        println("y")
                        LogEntry(
                            id = 0,
                            timestamp = System.currentTimeMillis(),
                            streak = logEntry.streak + 1
                        )
                    }else{
                        println("else")
                        LogEntry()
                    }
                } ?: LogEntry()
            }
            is Resource.Error -> {
                LogEntry()
            }
        }

        return logRepository.saveLogEntry(
            userId = userId,
            entry = newLogEntry
        )
    }
}