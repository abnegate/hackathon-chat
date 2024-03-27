package io.appwrite.messagewrite.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    val createdAt: String,
    val updatedAt: String,
    val collectionId: String,
    val databaseId: String,
    val userId: String,
    val name: String,
    val email: String,
    val photoUrl: String? = null,
)