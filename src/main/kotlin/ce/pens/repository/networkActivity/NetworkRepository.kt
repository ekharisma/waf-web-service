package ce.pens.repository.networkActivity

import ce.pens.entity.NetworkActivities
import ce.pens.entity.NetworkActivity
import ce.pens.entity.Users
import ce.pens.feature.DatabaseClient.dbQuery
import ce.pens.repository.BaseRepository
import mu.KotlinLogging
import org.jetbrains.exposed.sql.*

class NetworkRepository : BaseRepository<NetworkActivity, NetworkActivity> {
    private val logger = KotlinLogging.logger {}
    private fun resultRowToNetworkActivity(row: ResultRow) = NetworkActivity(
        ipSrc =  row[NetworkActivities.ipSrc],
        portSrc = row[NetworkActivities.portSrc],
        ipDst = row[NetworkActivities.ipDst],
        portDst = row[NetworkActivities.portDst],
        networkActivityName = row[NetworkActivities.networkActivityName]
    )

    override suspend fun create(entity: NetworkActivity) {
        logger.info { "Creating net record" }
        val insertRecord = dbQuery {
            NetworkActivities.insert {
                it[ipDst] = entity.ipDst
                it[portDst] = entity.portDst
                it[ipSrc] = entity.ipSrc
                it[portSrc] = entity.portSrc
                it[networkActivityName] = entity.networkActivityName
            }
        }
        insertRecord.resultedValues?.singleOrNull()?.let(::resultRowToNetworkActivity)
    }

    override suspend fun get(id: Int): NetworkActivity? {
        logger.info { "Get record by id: $id" }
        val record = dbQuery {
            NetworkActivities.select{
                NetworkActivities.id eq id
            }.map(::resultRowToNetworkActivity).singleOrNull()
        }
        return record
    }

    override suspend fun getAll(): List<NetworkActivity>? {
        logger.info { "Get all record" }
        return dbQuery {
            NetworkActivities.selectAll()
        }.map(::resultRowToNetworkActivity)
    }

    override suspend fun delete(id: Int) {
        logger.info { "Delete record: $id" }
        Users.deleteWhere { NetworkActivities.id eq id } > 0
    }

}