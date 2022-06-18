package ce.pens.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val username: String,
    val password: String
)

val userStorage = mutableListOf<User>()
