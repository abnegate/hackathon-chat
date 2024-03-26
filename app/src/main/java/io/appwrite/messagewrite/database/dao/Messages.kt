package io.appwrite.messagewrite.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.appwrite.messagewrite.models.db.Message

@Dao
interface Messages {
    @Query("SELECT * FROM Message")
    fun getAll(): List<Message>

    @Query("SELECT * FROM Message LIMIT :limit OFFSET :offset")
    fun getPaginated(limit: Int, offset: Int): List<Message>

    @Query("SELECT COUNT(*) FROM Message")
    fun getCount(): Long

    @Query("SELECT * FROM Message WHERE messageId IN (:messageIds)")
    fun getAllByIds(messageIds: IntArray): List<Message>

    @Query("SELECT * FROM Message WHERE messageId LIKE :id")
    fun getById(id: String): Message

    @Insert
    fun insertAll(vararg messages: Message)

    @Query("DELETE FROM Message WHERE messageId LIKE :id")
    fun deleteById(id: String)

    @Query("DELETE FROM Message")
    fun deleteAll()
}