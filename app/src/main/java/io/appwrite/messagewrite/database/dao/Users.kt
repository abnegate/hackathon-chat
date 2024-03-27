package io.appwrite.messagewrite.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.appwrite.messagewrite.models.db.User

@Dao
interface Users {
    @Query("SELECT * FROM User")
    fun getAll(): List<User>

    @Query("SELECT * FROM User LIMIT :limit OFFSET :offset")
    fun getPaginated(limit: Int, offset: Int): List<User>

    @Query("SELECT COUNT(*) FROM User")
    fun getCount(): Long

    @Query("SELECT * FROM User WHERE userId IN (:userIds)")
    fun getAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM User WHERE userId LIKE :id")
    fun getById(id: String): User?

    @Query("SELECT * FROM User WHERE email = :email")
    fun getByEmail(email: String): User?

    @Insert
    fun insertAll(vararg chats: User)

    @Query("DELETE FROM User WHERE userId LIKE :id")
    fun deleteById(id: String)

    @Query("DELETE FROM User")
    fun deleteAll()
}