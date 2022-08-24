package com.gmail.bogumilmecel2.data.database

import com.gmail.bogumilmecel2.diary_feature.domain.model.Price
import com.gmail.bogumilmecel2.diary_feature.domain.product.Product
import com.gmail.bogumilmecel2.diary_feature.domain.product.ProductTable
import org.ktorm.database.Database
import org.ktorm.dsl.from
import org.ktorm.dsl.innerJoin
import org.ktorm.dsl.map
import org.ktorm.dsl.select

class DatabaseManager {

    // config
    private val hostname = "vm-core.fritz.box"
    private val databaseName = "fitness_app"
    private val username = "fitness_app"
    private val password = System.getenv("FITNESS_APP_DB_PW")

    // database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getProducts():List<Product>{
        val items = ktormDatabase.from(ProductTable).select().map {
            Product(
                id = it[ProductTable.id] ?: 0,
                price = Price(id = 1)
            )
        }
        return items
    }

}