package ce.pens

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ce.pens.plugins.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.callloging.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureAuthentication()

        install(CallLogging)
    }.start(wait = true)
}
