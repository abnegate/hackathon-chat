package io.appwrite.messagewrite.repositories

import io.appwrite.ID
import io.appwrite.Permission
import io.appwrite.Query
import io.appwrite.Role
import io.appwrite.exceptions.AppwriteException
import io.appwrite.messagewrite.COLLECTION_CHATS
import io.appwrite.messagewrite.DATABASE_ID
import io.appwrite.messagewrite.cache.Cache
import io.appwrite.messagewrite.database.dao.Chats
import io.appwrite.messagewrite.models.Chat
import io.appwrite.messagewrite.models.db.Chat as DBChat
import io.appwrite.messagewrite.services.Network
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Databases
import javax.inject.Inject

class Chats @Inject constructor(
    private val databases: Databases,
    private val chatDao: Chats,
    private val cache: Cache<String, Document<Chat>>
) {
    @Throws(AppwriteException::class)
    suspend fun create(
        user1Id: String,
        user2Id: String
    ): Document<Chat> {
        val chatId = ID.unique()

        val chat = databases.createDocument(
            DATABASE_ID,
            COLLECTION_CHATS,
            chatId,
            Chat(user1Id, user2Id),
            listOf(
                Permission.read(Role.user(user1Id)),
                Permission.read(Role.user(user2Id)),
                Permission.update(Role.user(user1Id)),
                Permission.update(Role.user(user2Id)),
                Permission.delete(Role.user(user1Id)),
                Permission.delete(Role.user(user2Id))
            ),
            Chat::class.java
        )

        val dbChat = DBChat(
            chatId = chatId,
            createdAt = chat.createdAt,
            updatedAt = chat.updatedAt,
            databaseId = chat.collectionId,
            collectionId = chat.databaseId,
            user1Id = user1Id,
            user2Id = user2Id
        )

        chatDao.insertAll(dbChat)

        cache[chat.id] = chat

        return chat
    }

    @Throws(AppwriteException::class)
    suspend fun get(chatId: String): Document<Chat> {
        cache[chatId]?.let { return it }

        if (!Network.isInternetAvailable()) {
            val dbChat = chatDao.getById(chatId)

            val chat = Document(
                dbChat.chatId,
                dbChat.createdAt,
                dbChat.updatedAt,
                dbChat.collectionId,
                dbChat.databaseId,
                listOf(),
                Chat(
                    dbChat.user1Id,
                    dbChat.user2Id
                )
            )

            cache[chatId] = chat

            return chat
        }

        val chat = databases.getDocument(
            DATABASE_ID,
            COLLECTION_CHATS,
            chatId,
            null,
            Chat::class.java
        )

        cache[chatId] = chat

        return chat
    }

    @Throws(AppwriteException::class)
    suspend fun list(
        limit: Int? = null,
        offset: Int? = null
    ): DocumentList<Chat> {
        if (!Network.isInternetAvailable()) {
            val dbChats = chatDao.getPaginated(limit ?: 25, offset ?: 0)
            val dbTotal = chatDao.getCount()
            val chats = DocumentList(
                dbTotal,
                dbChats.map {
                    Document(
                        it.chatId,
                        it.createdAt,
                        it.updatedAt,
                        it.collectionId,
                        it.databaseId,
                        listOf(),
                        Chat(
                            it.user1Id,
                            it.user2Id
                        )
                    )
                }
            )

            for (chat in chats.documents) {
                cache[chat.id] = chat
            }

            return chats
        }

        val queries = mutableListOf<String>()
        limit?.let { queries.add(Query.limit(limit)) }
        offset?.let { queries.add(Query.offset(offset)) }

        val chats = databases.listDocuments(
            DATABASE_ID,
            COLLECTION_CHATS,
            queries,
            Chat::class.java
        )

        for (chat in chats.documents) {
            cache[chat.id] = chat
        }

        return chats
    }

    @Throws(AppwriteException::class)
    suspend fun delete(chatId: String) {
        cache.remove(chatId)
        chatDao.deleteById(chatId)
        databases.deleteDocument(
            DATABASE_ID,
            COLLECTION_CHATS,
            chatId
        )
    }
}