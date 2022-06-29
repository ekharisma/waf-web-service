package ce.pens.entity

import org.jetbrains.exposed.sql.Table

data class NetworkActivity(
    val ipSrc: String,
    val portSrc: Int,
    val ipDst: String,
    val portDst: Int,
    val networkActivityName: String
)

object NetworkActivities: Table() {
    val id = integer("id").autoIncrement()
    val ipSrc = varchar("ipSrc", 64)
    val portSrc = integer("portSrc")
    val ipDst = varchar("ipDst", 64)
    val portDst = integer("portDst")
    val networkActivityName = varchar("networkActivity", 64)

    override val primaryKey = PrimaryKey(id)
}
