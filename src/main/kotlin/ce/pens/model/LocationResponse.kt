package ce.pens.model

@kotlinx.serialization.Serializable
data class LocationResponse(
    val longitude: Float,
    val latitude: Float,
    val country: String
)
