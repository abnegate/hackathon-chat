package io.appwrite.messagewrite.models.views
data class User(
    val name: String,
    val email: String,
    val photoUrl: String? = null,
)