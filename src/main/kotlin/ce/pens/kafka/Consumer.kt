package ce.pens.kafka

import ce.pens.entity.NetworkActivityClass
import ce.pens.event.NetworkActivityEvent
import ce.pens.feature.ClosableJob
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.util.Properties
import mu.KotlinLogging
import org.apache.kafka.common.errors.WakeupException
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class Consumer<K, V>(private val consumer: KafkaConsumer<K, V>, topic: String) : ClosableJob {
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
        logger.info { "Consumer running..." }
       try {
           while (!closed.get()) {
               val records = consumer.poll(Duration.of(1000, ChronoUnit.MILLIS))
               for (record in records) {
                   logger.info { "Retrieve topic: ${record.topic()} : ${record.partition()}" }
                   logger.info { "Process message" }
                   val obj = Json.decodeFromString<NetworkActivityEvent>(record.value().toString())
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

fun <K, V> buildConsumer(host: String, clientId: String, groupId: String, topic: String): Consumer<K, V> {
    val consumerProps = Properties()
    consumerProps.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, host)
    consumerProps.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, clientId)
    consumerProps.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    consumerProps.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    consumerProps.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java.name)
    return Consumer(KafkaConsumer(consumerProps), topic)
}