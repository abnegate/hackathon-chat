package io.appwrite.messagewrite.models.network

data class Message(
    val chatId: String,
    val senderId: String,
    val receiverId: String,
    val content: String,
)
