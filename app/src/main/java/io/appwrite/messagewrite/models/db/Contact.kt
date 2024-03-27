package io.appwrite.messagewrite.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val createdAt: String,
    val updatedAt: String,
    val collectionId: String,
    val databaseId: String,
    val contactId: String,
    val ownerId: String,
    val userId: String,
)
