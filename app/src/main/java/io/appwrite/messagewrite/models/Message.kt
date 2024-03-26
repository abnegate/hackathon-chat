package io.appwrite.messagewrite.models

data class Message(
    val chatId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long,
)
