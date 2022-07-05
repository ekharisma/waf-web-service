package ce.pens.entity.response

@kotlinx.serialization.Serializable
data class LocationResponse(
    val latitude: Float,
    val longitude: Float,
    val country: String
)
