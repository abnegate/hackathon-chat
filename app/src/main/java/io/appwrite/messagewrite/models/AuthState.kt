package io.appwrite.messagewrite.models

import io.appwrite.models.User

data class AuthState(
    var user: User<Map<String, Any>>? = null
)