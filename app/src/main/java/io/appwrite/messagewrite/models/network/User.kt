package io.appwrite.messagewrite.models.network

data class User(
    val name: String,
    val email: String,
    val photoUrl: String? = null,
)