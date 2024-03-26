package io.appwrite.messagewrite.cache

data class Entry<T>(
    val value: T,
    val timestamp: Long = System.currentTimeMillis(),
) {
    fun isExpired(maxAge: Long): Boolean {
        return System.currentTimeMillis() - timestamp > maxAge
    }
}