package io.appwrite.messagewrite.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Chat(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val createdAt: String,
    val updatedAt: String,
    val collectionId: String,
    val databaseId: String,
    val chatId: String,
    val user1Id: String,
    val user2Id: String,
    val lastMessageId: String?,
)
