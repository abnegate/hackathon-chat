package io.appwrite.messagewrite.models.views

data class Chat(
    val id: String,
    val profileImageUrl: String,
    val username: String,
    val lastMessage: String,
    val lastMessageTime: String,
)
