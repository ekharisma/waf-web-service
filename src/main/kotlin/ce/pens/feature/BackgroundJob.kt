package ce.pens.feature

import io.ktor.server.application.*
import io.ktor.util.*
import mu.KotlinLogging
import java.io.Closeable
import kotlin.concurrent.thread

class BackgroundJob(configuration: JobConfiguration) : Closeable {
        private val job = configuration.job
        private val name = configuration.name
        private val logger = KotlinLogging.logger {}

        class JobConfiguration {
                var name: String? = null
                var job: ClosableJob? = null
        }

        object KafkaBackgroundJob : Plugin<Application, JobConfiguration, BackgroundJob> {
                override val key: AttributeKey<BackgroundJob>
                        get() = AttributeKey("KafkaBackgroundJob")
                override fun install(pipeline: Application, configure: JobConfiguration.() -> Unit): BackgroundJob {
                        val configuration = JobConfiguration().apply(configure)
                        val backgroundJob = BackgroundJob(configuration)
                        configuration.job?.let {
                                thread(name = configuration.name) {
                                        it.run()
                                }
                        }
                        return backgroundJob
                }
        }
        override fun close() {
                logger.info { "Closing Job" }
                job?.close()
                logger.info { "Job Closed" }
        }

}