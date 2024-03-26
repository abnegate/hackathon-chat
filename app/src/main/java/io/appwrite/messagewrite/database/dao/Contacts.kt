package io.appwrite.messagewrite.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.appwrite.messagewrite.models.db.Chat
import io.appwrite.messagewrite.models.db.Contact

@Dao
interface Contacts {
    @Query("SELECT * FROM Contact")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM Contact LIMIT :limit OFFSET :offset")
    fun getPaginated(limit: Int, offset: Int): List<Contact>

    @Query("SELECT COUNT(*) FROM Contact")
    fun getCount(): Long

    @Query("SELECT * FROM Contact WHERE contactId IN (:contactIds)")
    fun getAllByIds(contactIds: IntArray): List<Contact>

    @Query("SELECT * FROM Contact WHERE contactId LIKE :id")
    fun getById(id: String): Contact

    @Insert
    fun insertAll(vararg contacts: Contact)

    @Query("DELETE FROM Contact WHERE contactId LIKE :id")
    fun deleteById(id: String)

    @Query("DELETE FROM Contact")
    fun deleteAll()
}