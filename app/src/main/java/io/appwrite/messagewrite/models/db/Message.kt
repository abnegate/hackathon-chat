package io.appwrite.messagewrite.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val createdAt: String,
    val updatedAt: String,
    val collectionId: String,
    val databaseId: String,
    val messageId: String,
    val chatId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long,
)
