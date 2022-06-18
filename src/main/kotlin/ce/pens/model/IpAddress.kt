package ce.pens.model

import kotlinx.serialization.Serializable

@Serializable
data class IpAddress(
    val ipAddress: String,
)
