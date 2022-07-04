package ce.pens.routes

import ce.pens.entity.response.WebResponse
import ce.pens.repository.networkActivity.NetworkRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging
import org.jetbrains.exposed.sql.exposedLogger

fun Route.networkActivityRoute() {
    val dao = NetworkRepository()
    val logger = KotlinLogging.logger {}
    route("/activity/{limit}") {
        get {
            var limit = 10
            if (call.parameters["limit"] != "") {
               limit = call.parameters["limit"]?.toInt() ?: 10
            }
            val activity = dao.getAll(limit)
            val response = WebResponse(
                status = "ok",
                message = activity
            )
            return@get call.respond(response)
        }
    }

    route("/activity/count") {
        get {
            var timeframe = call.request.queryParameters["timeframe"]
            if (timeframe == null) {
                timeframe = "daily"
            }
            val recordCount = dao.getAll(timeframe)
            logger.info {"Record: $recordCount"}
            val response = WebResponse(
                status = "ok",
                message = recordCount
            )
            return@get call.respond(response)
        }
    }
}