package ce.pens.feature

import io.ktor.server.application.*


val KafkaBackgroundJob = createApplicationPlugin("KafkaBackgroundJob") {
        println("Kafka Background Job is running")
}