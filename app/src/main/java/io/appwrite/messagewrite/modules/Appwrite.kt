package io.appwrite.messagewrite.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Databases
import io.appwrite.services.Functions
import io.appwrite.services.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Appwrite {

    private const val ENDPOINT = "https://cloud.appwrite.io/v1"
    private const val PROJECT_ID = "messagewrite"

    @Provides
    @Singleton
    fun provideClient(@ApplicationContext context: Context) =
        Client(context)
            .setEndpoint(ENDPOINT)
            .setProject(PROJECT_ID)

    @Provides
    fun provideDatabases(client: Client) = Databases(client)

    @Provides
    fun provideStorage(client: Client) = Storage(client)

    @Provides
    fun provideFunctions(client: Client) = Functions(client)

//    @Provides
//    fun provideMessaging(client: Client) = Messaging(client)

}