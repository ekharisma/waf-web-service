package ce.pens.plugins

import ce.pens.routes.authRoute
import ce.pens.routes.geolocationRoutes
import ce.pens.routes.networkActivityRoute
import ce.pens.routes.userRouting
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        userRouting()
        authRoute()
        geolocationRoutes()
        networkActivityRoute()
    }
}
