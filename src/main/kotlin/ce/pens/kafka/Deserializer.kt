package ce.pens.kafka

import ce.pens.event.NetworkActivityEvent
import com.sun.xml.internal.ws.encoding.soap.DeserializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.kafka.common.serialization.Deserializer

class Deserializer: Deserializer<NetworkActivityEvent?> {
    override fun deserialize(topic: String?, data: ByteArray?): NetworkActivityEvent? {
        try {
            if (data == null) {
                return null
            }
            val payload = data.toString()
            return Json.decodeFromString(payload)
        } catch (e: Exception) {
            throw DeserializationException("Error deserialising JSON message $e")
        }
    }

}