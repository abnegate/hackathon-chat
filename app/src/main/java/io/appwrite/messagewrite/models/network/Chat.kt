package io.appwrite.messagewrite.models.network

data class Chat(
    val user1Id: String,
    val user2Id: String,
    val lastMessageId: String? = null,
)
