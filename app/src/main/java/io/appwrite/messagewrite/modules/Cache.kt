package io.appwrite.messagewrite.modules

import io.appwrite.messagewrite.cache.Cache
import io.appwrite.messagewrite.cache.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.appwrite.messagewrite.models.Chat
import io.appwrite.messagewrite.models.Contact
import io.appwrite.messagewrite.models.Message
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
}