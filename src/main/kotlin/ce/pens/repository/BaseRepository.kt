package ce.pens.repository

import ce.pens.entity.User

interface BaseRepository<in C, out T> {
    suspend fun create(entity: C)
    suspend fun get(id: Int): T?
    suspend fun getAll(): List<T>?
    suspend fun delete(id: Int)
}