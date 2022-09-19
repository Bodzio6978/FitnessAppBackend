package com.gmail.bogumilmecel2.user.log.data.repository

import com.gmail.bogumilmecel2.common.exception.NoDatabaseEntryException
import com.gmail.bogumilmecel2.common.util.Resource
import com.gmail.bogumilmecel2.user.log.data.table.LogTable
import com.gmail.bogumilmecel2.user.log.domain.model.LogEntry
import com.gmail.bogumilmecel2.user.log.domain.repository.LogRepository
import org.ktorm.database.Database
import org.ktorm.dsl.*

class LogRepositoryImp(
    private val database: Database
):LogRepository {

    override suspend fun saveLogEntry(entry: LogEntry, userId: Int): Resource<LogEntry> {
        return try {
            val key = database.insertAndGenerateKey(LogTable){
                set(it.timestamp, entry.timestamp)
                set(it.userId, userId)
                set(it.streak, entry.streak)
            } as Int
            Resource.Success(
                data = LogEntry(
                    id = key,
                    streak = entry.streak,
                    timestamp = entry.timestamp
                )
            )
        }catch (e:Exception){
            Resource.Error(e)
        }

    }

    override suspend fun getLatestLogEntry(userId: Int): Resource<LogEntry> {
        return try {
            val query = database.from(LogTable)
                .select()
                .where {
                    LogTable.userId eq userId
                }
                .orderBy(LogTable.timestamp.desc())
                .limit(1)
                .map {
                    LogEntry(
                        id = it[LogTable.id] ?: -1,
                        timestamp = it[LogTable.timestamp] ?: System.currentTimeMillis(),
                        streak = it[LogTable.streak] ?: -1
                    )
                }
            if (query.isNotEmpty()){
                Resource.Success(data = query[0])
            }else{
                Resource.Error(NoDatabaseEntryException())
            }
        }catch (e:Exception){
            Resource.Error(e)
        }
    }
}