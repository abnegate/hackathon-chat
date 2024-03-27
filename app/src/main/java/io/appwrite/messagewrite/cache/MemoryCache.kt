package io.appwrite.messagewrite.cache

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedDeque

class MemoryCache<TKey, TValue>(
    override val maxSize: Int = Int.MAX_VALUE,
    override val maxAge: Long = Long.MAX_VALUE
) : Cache<TKey, TValue> {
    private val cache = ConcurrentHashMap<TKey, Entry<TValue>>()
    private val keys = ConcurrentLinkedDeque<TKey>()

    override operator fun set(key: TKey, value: TValue) {
        if (cache.size >= maxSize) {
            keys.pollLast()?.let { cache.remove(it) }
        }
        cache[key] = Entry(value)
        keys.offerFirst(key)
    }

    override operator fun get(key: TKey): TValue? {
        return cache[key]?.let {
            if (it.isExpired(maxAge)) {
                cache.remove(key)
                keys.remove(key)
                return null
            }

            keys.remove(key)
            keys.offerFirst(key)

            return it.value
        }
    }

    override fun find(func: (TValue) -> Boolean): TValue? {
        return cache.values.find { !it.isExpired(maxAge) && func(it.value) }?.value
    }

    override fun remove(key: TKey) {
        cache.remove(key)
        keys.remove(key)
    }

    override fun clear() {
        cache.clear()
        keys.clear()
    }
}