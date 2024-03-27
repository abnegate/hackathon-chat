package io.appwrite.messagewrite.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.appwrite.messagewrite.models.AuthState
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Auth {
    @Provides
    @Singleton
    fun provideAuthState() = AuthState()
}