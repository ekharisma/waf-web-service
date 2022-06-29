package ce.pens.routes

import ce.pens.entity.GeoLocation
import ce.pens.entity.request.IpAddressRequest
import ce.pens.entity.response.LocationResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.netty.handler.codec.http.HttpResponseStatus
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun Route.geolocationRoutes() {
    route("/location") {
        post {
            val ipAddressRequest = call.receive<IpAddressRequest>()
            val url = "https://ipwhois.app/json/${ipAddressRequest.ipAddress}"
            val client = HttpClient(CIO)
            val response: HttpResponse = client.get(url)
            call.application.environment.log.info("Response: ${response.status}")
            if (response.status.value == HttpResponseStatus.OK.code()) {
                val geoLocationResponse : String = response.body()
                val obj = Json.decodeFromString<GeoLocation>(geoLocationResponse)
                call.respond(HttpStatusCode.OK, LocationResponse(
                    longitude = obj.longitude,
                    latitude = obj.latitude,
                    country = obj.country
                )
                )
            }
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}