package ce.pens.entity

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

@Serializable
data class NetworkActivity(
    val id: String,
    val ipSrc: String,
    val portSrc: Int,
    val ipDst: String,
    val portDst: Int,
    val networkActivityName: String,
    val timestamp: String,
    val createdAt: String
)

object NetworkActivities: Table() {
    val id = varchar("id", 256)
    val ipSrc = varchar("ipSrc", 64)
    val portSrc = integer("portSrc")
    val ipDst = varchar("ipDst", 64)
    val portDst = integer("portDst")
    val networkActivityName = varchar("networkActivity", 64)
    val timestamp = varchar("timestamp", 64)
    val createdAt = datetime("createdAt")

    override val primaryKey = PrimaryKey(id)
}
