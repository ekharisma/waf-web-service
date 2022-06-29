package ce.pens.repository.User

import ce.pens.entity.User

interface IUserInterface {
    suspend fun getByUsername(username: String): User?
}