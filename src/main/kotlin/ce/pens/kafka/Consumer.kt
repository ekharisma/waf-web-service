package ce.pens.kafka

import ce.pens.feature.CloseableJob
import io.ktor.server.application.*
import io.ktor.utils.io.core.*
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import mu.KotlinLogging
import org.apache.kafka.common.errors.WakeupException
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class Consumer<K, V>(private val consumer: KafkaConsumer<K, V>, topic: String) : CloseableJob {
    private val logger = KotlinLogging.logger {}
    private val closed: AtomicBoolean = AtomicBoolean(false)
    private val finished = CountDownLatch(1)

    init {
        consumer.subscribe(listOf(topic))
    }

    override fun close() {
        logger.info { "Closing job ..." }
        closed.set(true)
        consumer.wakeup()
        finished.await(3_000, TimeUnit.MILLISECONDS)
        logger.info { "Job closed" }
    }

    override fun run() {
       try {
           while (!closed.get()) {
               val records = consumer.poll(Duration.of(1000, ChronoUnit.MILLIS))
               for (record in records) {
                   logger.info { "topic: ${record.topic()} : ${record.partition()}" }
               }
               if (!records.isEmpty) {
                   consumer.commitAsync {offsets, exception ->
                       if (exception != null) {
                           logger.error { "commit failed for offsets: $offsets" }
                       } else {
                           logger.info { "Offset commited $offsets" }
                       }
                   }
               }
           }
       } catch (e: Throwable) {
           when(e) {
               is WakeupException -> logger.info { "Consumer waked up" }
               else -> logger.error { "Polling failed" }
           }
       } finally {
           logger.info { "Offset commited synchronously" }
           consumer.commitAsync()
           consumer.close()
           finished.countDown()
           logger.info { "Consummer successfully closed" }
       }
    }
}

fun <K, V> buildConsumer(environment: ApplicationEnvironment): Consumer<K, V> {
    val consumerConfig = environment.config.config("ktor.kafka.consumer")
    val consumerProps = Properties().apply {
        this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = consumerConfig.property("bootstrap.servers").getList()
        this[ConsumerConfig.CLIENT_ID_CONFIG] = consumerConfig.property("client.id").getString()
        this[ConsumerConfig.GROUP_ID_CONFIG] = consumerConfig.property("group.id").getString()
        this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = consumerConfig.property("key.deserializer").getString()
        this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = consumerConfig.property("value.deserializer").getString()
    }
    return Consumer(KafkaConsumer(consumerProps), consumerConfig.property("topic").getString())
}