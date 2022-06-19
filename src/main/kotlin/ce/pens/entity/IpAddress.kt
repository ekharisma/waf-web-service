package ce.pens.entity

import kotlinx.serialization.Serializable

@Serializable
data class IpAddress(
    val ipAddress: String,
)
