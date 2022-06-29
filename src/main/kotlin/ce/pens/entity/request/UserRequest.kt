package ce.pens.entity.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest (
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)