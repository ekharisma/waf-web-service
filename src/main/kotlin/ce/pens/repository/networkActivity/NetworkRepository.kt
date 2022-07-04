package ce.pens.repository.networkActivity

import ce.pens.entity.NetworkActivities
import ce.pens.entity.NetworkActivity
import ce.pens.feature.DatabaseClient.dbQuery
import ce.pens.repository.BaseRepository
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.between
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.less
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.time.LocalDateTime
import java.util.*
import kotlin.text.Typography.less

class NetworkRepository : BaseRepository<NetworkActivity, NetworkActivity> {
    private val logger = KotlinLogging.logger {}

    override suspend fun create(entity: NetworkActivity) {
        logger.info { "Creating net record" }
        val insertRecord = dbQuery {
            NetworkActivities.insert {
                it[id] = UUID.randomUUID().toString()
                it[ipDst] = entity.ipDst
                it[portDst] = entity.portDst
                it[ipSrc] = entity.ipSrc
                it[portSrc] = entity.portSrc
                it[networkActivityName] = entity.networkActivityName
                it[timestamp] = entity.timestamp
                it[createdAt] = LocalDateTime.now()
            }
        }
        insertRecord.resultedValues?.singleOrNull()?.let(::resultRowToNetworkActivity)
    }

    override suspend fun get(id: Int): NetworkActivity {
        logger.info { "Get record by id: $id" }
        return NetworkActivity("", "", 0, "", 0, "", "", "")
    }

    suspend fun get(id: String): NetworkActivity? {
        logger.info { "Get record by id: $id" }
        val record = dbQuery {
            NetworkActivities.select{
                NetworkActivities.id eq id
            }.map{ resultRowToNetworkActivity(it) }.singleOrNull()
        }
        return record
    }

    override suspend fun getAll(): List<NetworkActivity> {
        logger.info { "Get all record" }
        val networkActivities = dbQuery {
            NetworkActivities.selectAll()
        }.map{ resultRowToNetworkActivity(it) }
        return networkActivities
    }

    suspend fun getAll(timeframe: String): Long {
        logger.info { "Get all record by timeframe" }
        when(timeframe) {
            "daily" -> return getRecordByDay()
            "monthly" -> return getRecordByMonth()
            "yearly" -> return getRecordByYear()
            else -> getRecordByDay()
        }
        return 0
    }

    private suspend fun getRecordByYear(): Long {
        logger.info { "Getting yearly record" }
        val date = LocalDateTime.now()
        val fromDate = LocalDateTime.parse("${date.year}-01-01T00:00:00")
        val toDate = LocalDateTime.parse("${date.year + 1}-01-01T00:00:00")
        logger.info { "Date: $fromDate - $toDate" }
        return dbQuery {
            NetworkActivities.selectAll().andWhere {
                NetworkActivities.createdAt.between(
                    fromDate, toDate
                )
            }.count()
        }
    }

    private suspend fun getRecordByMonth(): Long {
        logger.info { "Getting monthly record" }
        val date = LocalDateTime.now()
        val fromDate = LocalDateTime.parse("${date.year}-${formatString(date.monthValue)}-01T00:00:00")
        val toDate = LocalDateTime.parse("${date.year + 1}-${formatString(date.monthValue+1)}-01T00:00:00")
        logger.info { "Date: $fromDate - $toDate" }
        return dbQuery {
            NetworkActivities.selectAll().andWhere {
                NetworkActivities.createdAt.between(
                    fromDate, toDate
                )
            }.count()
        }
    }

    private suspend fun getRecordByDay(): Long {
        logger.info { "Getting monthly record" }
        val date = LocalDateTime.now()
        val fromDate = LocalDateTime.parse("${date.year}-${formatString(date.monthValue)}-${formatString(date.dayOfMonth)}T00:00:00")
        val toDate = LocalDateTime.parse("${date.year + 1}-${formatString(date.monthValue+1)}-${formatString(date.dayOfMonth + 1)}T00:00:00")
        logger.info { "Date: $fromDate - $toDate" }
        return dbQuery {
            NetworkActivities.selectAll().andWhere {
                NetworkActivities.createdAt.between(
                    fromDate, toDate
                )
            }.count()
        }
    }

    suspend fun getAll(limit: Int): List<NetworkActivity> {
        logger.info { "Get Records with $limit limit" }
        return dbQuery {
            NetworkActivities.selectAll().limit(limit).map { resultRowToNetworkActivity(it) }
        }
    }

    override suspend fun delete(id: Int) {
        logger.info { "Delete record: $id" }
    }

    private fun resultRowToNetworkActivity(row: ResultRow) = NetworkActivity(
        id = row[NetworkActivities.id],
        ipSrc =  row[NetworkActivities.ipSrc],
        portSrc = row[NetworkActivities.portSrc],
        ipDst = row[NetworkActivities.ipDst],
        portDst = row[NetworkActivities.portDst],
        networkActivityName = row[NetworkActivities.networkActivityName],
        timestamp = row[NetworkActivities.timestamp],
        createdAt = row[NetworkActivities.createdAt].toString()
    )

    private fun formatString(value: Int) : String {
        if (value > 9) return value.toString()
        else return "0$value"
    }
}