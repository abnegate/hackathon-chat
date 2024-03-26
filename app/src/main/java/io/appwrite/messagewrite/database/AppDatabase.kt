package io.appwrite.messagewrite.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.appwrite.messagewrite.database.dao.Chats
import io.appwrite.messagewrite.database.dao.Contacts
import io.appwrite.messagewrite.database.dao.Messages
import io.appwrite.messagewrite.models.Chat
import io.appwrite.messagewrite.models.Contact
import io.appwrite.messagewrite.models.Message

@Database(
    entities = [
        Chat::class,
        Contact::class,
        Message::class,
    ],
    version = AppDatabase.VERSION
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val VERSION = 1
    }

    abstract fun chatDao(): Chats
    abstract fun contactDao(): Contacts
    abstract fun messageDao(): Messages
}