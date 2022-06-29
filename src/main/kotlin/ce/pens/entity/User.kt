package ce.pens.entity

import org.jetbrains.exposed.sql.Table

data class User(
    val username: String,
    val password: String
)

object Users: Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("name", 64)
    val password = varchar("password", 512)
    override val primaryKey = PrimaryKey(id)
}
