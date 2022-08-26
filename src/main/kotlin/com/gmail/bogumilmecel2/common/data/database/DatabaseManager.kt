package com.gmail.bogumilmecel2.common.data.database

import com.gmail.bogumilmecel2.diary_feature.domain.model.price.Price
import com.gmail.bogumilmecel2.diary_feature.domain.model.product.Product
import com.gmail.bogumilmecel2.diary_feature.data.table.product.ProductTable
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.map
import org.ktorm.dsl.select

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