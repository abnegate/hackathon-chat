package io.appwrite.messagewrite.models.views

data class Message(
    val chatId: String,
    val senderId: String,
    val text: String,
    val timestamp: Long,
)
