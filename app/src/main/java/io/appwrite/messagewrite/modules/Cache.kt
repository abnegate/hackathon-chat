package io.appwrite.messagewrite.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.appwrite.messagewrite.cache.Cache
import io.appwrite.messagewrite.cache.MemoryCache
import io.appwrite.messagewrite.models.network.Chat
import io.appwrite.messagewrite.models.network.Contact
import io.appwrite.messagewrite.models.network.Message
import io.appwrite.messagewrite.models.network.User
import io.appwrite.models.Document
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Cache {
    @Provides
    @Singleton
    fun provideChatsCache(): Cache<String, Document<Chat>> = MemoryCache()

    @Provides
    @Singleton
    fun provideContactsCache(): Cache<String, Document<Contact>> = MemoryCache()

    @Provides
    @Singleton
    fun provideMessageCache(): Cache<String, Document<Message>> = MemoryCache()

    @Provides
    @Singleton
    fun provideUserCache(): Cache<String, Document<User>> = MemoryCache()
}