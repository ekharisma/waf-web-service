package ce.pens.entity.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebResponse<T>(
    @SerialName("status")
    val status: String,
    @SerialName("message")
    val message: T
)