package io.appwrite.messagewrite.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.messagewrite.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Database {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "messagewrite"
        ).build()

    @Provides
    fun provideChatDao(appDatabase: AppDatabase) = appDatabase.chatDao()

    @Provides
    fun provideContactDao(appDatabase: AppDatabase) = appDatabase.contactDao()

    @Provides
    fun provideMessageDao(appDatabase: AppDatabase) = appDatabase.messageDao()
}