package ce.pens

import ce.pens.feature.BackgroundJob
import ce.pens.feature.DatabaseClient
import ce.pens.kafka.buildConsumer
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
        DatabaseClient.init()
    }.start(wait = true)
}

fun Application.configureKafkaJobs() {
    install(BackgroundJob.KafkaBackgroundJob) {
        name = "Kafka-Background-Job"
        job = buildConsumer<String, String>(
            "localhost:9092", "kafka-producer", "kafka-group", "sniffer-data"
        )
    }
}
