package ce.pens.plugins

import ce.pens.feature.KafkaBackgroundJob
import io.ktor.server.application.*

fun Application.configureJobs() {
    install(KafkaBackgroundJob)
}