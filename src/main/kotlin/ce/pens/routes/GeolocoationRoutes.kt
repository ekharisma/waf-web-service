package ce.pens.routes

import ce.pens.model.GeoLocationResponse
import ce.pens.model.IpAddress
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.geolocationRoutes() {
    route("/location") {
        post {
            val ipAddress = call.receive<IpAddress>()
            val url = "https://ipwhois.app/json/${ipAddress.ipAddress}"
            val client = HttpClient(CIO)
            call.application.environment.log.info("URL: $url")
            val response = client.get(url).body<GeoLocationResponse>()
            client.close()
            call.application.environment.log.info("Getting response ready")
            if (!response.success) {
                call.application.environment.log.error("Failed to make request")
                call.respondText("Failed to make request", status= HttpStatusCode.BadRequest)
            }
            call.respond(response)
        }
    }
}