package ce.pens.entity.request

import kotlinx.serialization.Serializable

@Serializable
data class IpAddressRequest(
    val ipAddress: String,
)
