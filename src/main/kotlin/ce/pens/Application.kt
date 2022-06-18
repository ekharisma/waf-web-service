package ce.pens

import ce.pens.feature.KafkaBackgroundJob
import
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ce.pens.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.callloging.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
        configureAuthentication()
        configureKafkaJobs()
        install(CallLogging)
    }.start(wait = true)
}

fun Application.configureKafkaJobs() {
    install(KafkaBackgroundJob) {
        name = "Kafka-Consumer-Job"
        job = buildConsumer
    }
}
