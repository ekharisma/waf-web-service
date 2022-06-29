package ce.pens.feature

import ce.pens.entity.NetworkActivities
import ce.pens.entity.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseClient {
    fun init() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://localhost:5432/webservice-db"
        val username = "postgres"
        val password = "postgres"
        val database = Database.connect(jdbcURL, driverClassName, username, password)
        transaction(database) {
            SchemaUtils.create(Users)
            SchemaUtils.create(NetworkActivities)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) {
            block()
        }
}