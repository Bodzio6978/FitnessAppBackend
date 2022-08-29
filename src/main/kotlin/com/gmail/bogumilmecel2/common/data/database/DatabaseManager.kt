package com.gmail.bogumilmecel2.common.data.database

import org.ktorm.database.Database

class DatabaseManager {

    // config
    private val hostname = "localhost"
    private val databaseName = "fitness_app"
    private val username = "root"
    private val password = "root"

    // database
    var ktormDatabase: Database
        private set

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false&allowPublicKeyRetrieval=true"
        ktormDatabase = Database.connect(jdbcUrl)
    }
}