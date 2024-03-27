package io.appwrite.messagewrite.cache

interface Cache<TKey, TValue> {
    val maxSize: Int
    val maxAge: Long

    operator fun set(key: TKey, value: TValue)
    operator fun get(key: TKey): TValue?
    fun find(func: (TValue) -> Boolean): TValue?
    fun remove(key: TKey)
    fun clear()
}