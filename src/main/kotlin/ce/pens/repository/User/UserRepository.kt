package ce.pens.repository.User

import ce.pens.entity.User
import ce.pens.entity.Users
import ce.pens.feature.DatabaseClient.dbQuery
import ce.pens.repository.BaseRepository
import ce.pens.util.HashUtil
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository: BaseRepository<User, User>, IUserInterface {
    private fun resultRowToUsers(row: ResultRow) = User(
        username = row[Users.username],
        password = row[Users.password]
    )
    private val logger = KotlinLogging.logger {}

    override suspend fun create(entity: User) {
        logger.info { "Creating new user..." }
        val insertUser = transaction {
            Users.insert {
                it[username] = entity.username
                it[password] = HashUtil.sha1(entity.password)
            }
        }
        insertUser.resultedValues?.singleOrNull()?.let(::resultRowToUsers)
    }

    override suspend fun get(id: Int): User? {
        logger.info { "Get user with id: $id" }
        val user = transaction {  Users.select {
                Users.id eq id
            }.map(::resultRowToUsers).singleOrNull()
        }
        logger.info { "User found: $user" }
        return user
    }

    override suspend fun getByUsername(username: String): User? {
        logger.info { "Get user with username: $username" }
        val user = dbQuery { Users.select {
            Users.username eq username
        }.map(::resultRowToUsers).singleOrNull()
        }
        return user
    }

    override suspend fun getAll(): List<User> {
        logger.info { "Get all user" }
        return dbQuery {
            Users.selectAll()
        }.map(::resultRowToUsers)
    }

    override suspend fun delete(id: Int) {
        logger.info { "Delete user: $id" }
        Users.deleteWhere { Users.id eq id } > 0
    }
}