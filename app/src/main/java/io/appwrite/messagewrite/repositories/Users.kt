package io.appwrite.messagewrite.repositories

import io.appwrite.Permission
import io.appwrite.Query
import io.appwrite.Role
import io.appwrite.exceptions.AppwriteException
import io.appwrite.messagewrite.COLLECTION_USERS
import io.appwrite.messagewrite.DATABASE_ID
import io.appwrite.messagewrite.cache.Cache
import io.appwrite.messagewrite.database.dao.Users
import io.appwrite.messagewrite.models.network.User
import io.appwrite.messagewrite.services.Network
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Databases
import javax.inject.Inject
import io.appwrite.messagewrite.models.db.User as DBUser

class Users @Inject constructor(
    private val databases: Databases,
    private val userDao: Users,
    private val cache: Cache<String, Document<User>>
) {
    @Throws(AppwriteException::class)
    suspend fun create(
        userId: String,
        name: String,
        email: String,
        photoUrl: String? = null
    ): Document<User> {
        val user = databases.createDocument(
            DATABASE_ID,
            COLLECTION_USERS,
            userId,
            User(
                name,
                email,
                photoUrl
            ),
            listOf(
                Permission.read(Role.users()),
                Permission.update(Role.user(userId)),
                Permission.delete(Role.user(userId)),
            ),
            User::class.java
        )

        val dbUser = DBUser(
            userId = userId,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            databaseId = user.collectionId,
            collectionId = user.databaseId,
            name = name,
            email = email,
            photoUrl = photoUrl
        )

        userDao.insertAll(dbUser)

        cache[user.id] = user

        return user
    }

    @Throws(AppwriteException::class)
    suspend fun get(userId: String): Document<User>? {
        cache[userId]?.let { return it }

        if (!Network.isInternetAvailable()) {
            val dbUser = userDao.getById(userId) ?: return null

            val user = Document(
                dbUser.userId,
                dbUser.createdAt,
                dbUser.updatedAt,
                dbUser.collectionId,
                dbUser.databaseId,
                listOf(),
                User(
                    dbUser.name,
                    dbUser.email,
                    dbUser.photoUrl
                )
            )

            cache[userId] = user

            return user
        }

        val user = try {
            databases.getDocument(
                DATABASE_ID,
                COLLECTION_USERS,
                userId,
                null,
                User::class.java
            )
        } catch (e: AppwriteException) {
            return null
        }

        cache[userId] = user

        return user
    }

    @Throws(AppwriteException::class)
    suspend fun getByEmail(email: String): Document<User>? {
        cache.find { it.data.email == email }?.let { return it }

        if (!Network.isInternetAvailable()) {
            val dbUser = userDao.getByEmail(email) ?: return null

            val user = Document(
                dbUser.userId,
                dbUser.createdAt,
                dbUser.updatedAt,
                dbUser.collectionId,
                dbUser.databaseId,
                listOf(),
                User(
                    dbUser.name,
                    dbUser.email,
                    dbUser.photoUrl
                )
            )

            cache[user.id] = user

            return user
        }

        val users = try {
            databases.listDocuments(
                DATABASE_ID,
                COLLECTION_USERS,
                listOf(Query.equal("email", email)),
                User::class.java
            )
        } catch (e: AppwriteException) {
            return null
        }

        if (users.documents.isEmpty()) {
            return null
        }

        val user = users.documents.first()

        cache[user.id] = user

        return user
    }

    @Throws(AppwriteException::class)
    suspend fun list(
        limit: Int? = null,
        offset: Int? = null
    ): DocumentList<User> {
        if (!Network.isInternetAvailable()) {
            val dbUsers = userDao.getPaginated(limit ?: 100, offset ?: 0)
            val dbTotal = userDao.getCount()
            val users = DocumentList(
                dbTotal,
                dbUsers.map {
                    Document(
                        it.userId,
                        it.createdAt,
                        it.updatedAt,
                        it.collectionId,
                        it.databaseId,
                        listOf(),
                        User(
                            it.name,
                            it.email,
                            it.photoUrl
                        )
                    )
                }
            )

            for (user in users.documents) {
                cache[user.id] = user
            }

            return users
        }

        val queries = mutableListOf<String>()
        limit?.let { queries.add(Query.limit(limit)) }
        offset?.let { queries.add(Query.offset(offset)) }

        val users = databases.listDocuments(
            DATABASE_ID,
            COLLECTION_USERS,
            queries,
            User::class.java
        )

        for (user in users.documents) {
            cache[user.id] = user
        }

        return users
    }

    @Throws(AppwriteException::class)
    suspend fun delete(userId: String) {
        cache.remove(userId)
        userDao.deleteById(userId)
        databases.deleteDocument(
            DATABASE_ID,
            COLLECTION_USERS,
            userId
        )
    }
}