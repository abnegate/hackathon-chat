package io.appwrite.messagewrite.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.appwrite.messagewrite.models.db.Chat

@Dao
interface Chats {
    @Query("SELECT * FROM Chat")
    fun getAll(): List<Chat>

    @Query("SELECT * FROM Chat LIMIT :limit OFFSET :offset")
    fun getPaginated(limit: Int, offset: Int): List<Chat>

    @Query("SELECT COUNT(*) FROM Chat")
    fun getCount(): Long

    @Query("SELECT * FROM Chat WHERE chatId IN (:chatIds)")
    fun getAllByIds(chatIds: IntArray): List<Chat>

    @Query("SELECT * FROM Chat WHERE chatId LIKE :id")
    fun getById(id: String): Chat

    @Insert
    fun insertAll(vararg chats: Chat)

    @Query("DELETE FROM Chat WHERE chatId LIKE :id")
    fun deleteById(id: String)

    @Query("DELETE FROM Chat")
    fun deleteAll()
}