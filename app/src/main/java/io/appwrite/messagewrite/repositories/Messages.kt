package io.appwrite.messagewrite.repositories

import io.appwrite.ID
import io.appwrite.Permission
import io.appwrite.Query
import io.appwrite.Role
import io.appwrite.exceptions.AppwriteException
import io.appwrite.messagewrite.COLLECTION_MESSAGES
import io.appwrite.messagewrite.DATABASE_ID
import io.appwrite.messagewrite.cache.Cache
import io.appwrite.messagewrite.database.dao.Messages
import io.appwrite.messagewrite.models.network.Message
import io.appwrite.messagewrite.services.Network
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Databases
import javax.inject.Inject
import io.appwrite.messagewrite.models.db.Message as DBMessage

class Messages @Inject constructor(
    private val databases: Databases,
    private val messageDao: Messages,
    private val cache: Cache<String, Document<Message>>
) {
    @Throws(AppwriteException::class)
    suspend fun create(
        chatId: String,
        senderId: String,
        receiverId: String,
        content: String
    ): Document<Message> {
        val messageId = ID.unique()

        val user = databases.createDocument(
            DATABASE_ID,
            COLLECTION_MESSAGES,
            messageId,
            Message(
                chatId,
                senderId,
                receiverId,
                content
            ),
            listOf(
                Permission.read(Role.user(senderId)),
                Permission.read(Role.user(receiverId)),
                Permission.delete(Role.user(senderId)),
            ),
            Message::class.java
        )

        val dbMessage = DBMessage(
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            databaseId = user.collectionId,
            collectionId = user.databaseId,
            messageId = messageId,
            chatId = chatId,
            senderId = senderId,
            receiverId = receiverId,
            content = content
        )

        messageDao.insertAll(dbMessage)

        cache[user.id] = user

        return user
    }

    @Throws(AppwriteException::class)
    suspend fun get(messageId: String): Document<Message> {
        cache[messageId]?.let { return it }

        if (!Network.isInternetAvailable()) {
            val dbMessage = messageDao.getById(messageId)

            val message = Document(
                dbMessage.messageId,
                dbMessage.createdAt,
                dbMessage.updatedAt,
                dbMessage.collectionId,
                dbMessage.databaseId,
                listOf(),
                Message(
                    dbMessage.chatId,
                    dbMessage.senderId,
                    dbMessage.receiverId,
                    dbMessage.content
                )
            )

            cache[messageId] = message

            return message
        }

        val message = databases.getDocument(
            DATABASE_ID,
            COLLECTION_MESSAGES,
            messageId,
            null,
            Message::class.java
        )

        cache[messageId] = message

        return message
    }

    @Throws(AppwriteException::class)
    suspend fun list(
        limit: Int? = null,
        offset: Int? = null
    ): DocumentList<Message> {
        if (!Network.isInternetAvailable()) {
            val dbMessages = messageDao.getPaginated(limit ?: 100, offset ?: 0)
            val dbTotal = messageDao.getCount()
            val messages = DocumentList(
                dbTotal,
                dbMessages.map {
                    Document(
                        it.messageId,
                        it.createdAt,
                        it.updatedAt,
                        it.collectionId,
                        it.databaseId,
                        listOf(),
                        Message(
                            it.chatId,
                            it.senderId,
                            it.receiverId,
                            it.content
                        )
                    )
                }
            )

            for (message in messages.documents) {
                cache[message.id] = message
            }

            return messages
        }

        val queries = mutableListOf<String>()
        limit?.let { queries.add(Query.limit(limit)) }
        offset?.let { queries.add(Query.offset(offset)) }

        val messages = databases.listDocuments(
            DATABASE_ID,
            COLLECTION_MESSAGES,
            queries,
            Message::class.java
        )

        for (message in messages.documents) {
            cache[message.id] = message
        }

        return messages
    }

    @Throws(AppwriteException::class)
    suspend fun delete(messageId: String) {
        cache.remove(messageId)
        messageDao.deleteById(messageId)
        databases.deleteDocument(
            DATABASE_ID,
            COLLECTION_MESSAGES,
            messageId
        )
    }
}