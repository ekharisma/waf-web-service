package ce.pens.entity

import kotlinx.serialization.Serializable

@Serializable
data class NetworkActivityClass(
    val id: String,
    val ipSrc: String,
    val portSrc: Int,
    val ipDst: String,
    val portDst: String,
    val networkActivityName: String
)

val networkActivityStorage = mutableListOf<NetworkActivityClass>()
