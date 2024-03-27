package io.appwrite.messagewrite.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.appwrite.messagewrite.database.dao.Chats
import io.appwrite.messagewrite.database.dao.Contacts
import io.appwrite.messagewrite.database.dao.Messages
import io.appwrite.messagewrite.database.dao.Users
import io.appwrite.messagewrite.models.db.Chat
import io.appwrite.messagewrite.models.db.Contact
import io.appwrite.messagewrite.models.db.Message
import io.appwrite.messagewrite.models.db.User

@Database(
    entities = [
        Chat::class,
        Contact::class,
        Message::class,
        User::class,
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
    abstract fun userDao(): Users
}