package io.appwrite.messagewrite.repositories

import io.appwrite.ID
import io.appwrite.Query
import io.appwrite.exceptions.AppwriteException
import io.appwrite.messagewrite.COLLECTION_CONTACTS
import io.appwrite.messagewrite.DATABASE_ID
import io.appwrite.messagewrite.cache.Cache
import io.appwrite.messagewrite.database.dao.Contacts
import io.appwrite.messagewrite.models.network.Contact
import io.appwrite.messagewrite.services.Network
import io.appwrite.models.Document
import io.appwrite.models.DocumentList
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Inject
import io.appwrite.messagewrite.models.db.Contact as DBContact

class Contacts @Inject constructor(
    private val account : Account,
    private val databases: Databases,
    private val contactDao: Contacts,
    private val cache: Cache<String, Document<Contact>>
) {

    @Throws(AppwriteException::class)
    suspend fun create(
        userId: String
    ): Document<Contact> {
        val currentUser = account.get()

        val contact = databases.createDocument(
            DATABASE_ID,
            COLLECTION_CONTACTS,
            ID.unique(),
            Contact(currentUser.id, userId),
            null,
            Contact::class.java
        )

        val dbContact = DBContact(
            contactId = contact.id,
            createdAt = contact.createdAt,
            updatedAt = contact.updatedAt,
            databaseId = contact.collectionId,
            collectionId = contact.databaseId,
            ownerId = currentUser.id,
            userId = userId
        )

        contactDao.insertAll(dbContact)

        cache[contact.id] = contact

        return contact
    }

    @Throws(AppwriteException::class)
    suspend fun get(contactId: String): Document<Contact> {
        cache[contactId]?.let { return it }

        if (!Network.isInternetAvailable()) {
            val dbContact = contactDao.getById(contactId)

            val contact = Document(
                dbContact.contactId,
                dbContact.createdAt,
                dbContact.updatedAt,
                dbContact.collectionId,
                dbContact.databaseId,
                listOf(),
                Contact(
                    dbContact.ownerId,
                    dbContact.userId,
                )
            )

            cache[contactId] = contact

            return contact
        }

        val contact = databases.getDocument(
            DATABASE_ID,
            COLLECTION_CONTACTS,
            contactId,
            null,
            Contact::class.java
        )

        cache[contactId] = contact

        return contact
    }

    @Throws(AppwriteException::class)
    suspend fun list(
        limit: Int? = null,
        offset: Int? = null
    ): DocumentList<Contact> {
        if (!Network.isInternetAvailable()) {
            val dbContacts = contactDao.getPaginated(limit ?: 10, offset ?: 0)
            val dbTotal = contactDao.getCount()

            val contacts = DocumentList(
                dbTotal,
                dbContacts.map {
                    Document(
                        it.contactId,
                        it.createdAt,
                        it.updatedAt,
                        it.collectionId,
                        it.databaseId,
                        listOf(),
                        Contact(
                            it.ownerId,
                            it.userId
                        )
                    )
                }
            )

            for (contact in contacts.documents) {
                cache[contact.id] = contact
            }

            return contacts
        }

        val queries = mutableListOf<String>()
        limit?.let { queries.add(Query.limit(limit)) }
        offset?.let { queries.add(Query.offset(offset)) }

        val contacts = databases.listDocuments(
            DATABASE_ID,
            COLLECTION_CONTACTS,
            queries,
            Contact::class.java
        )

        for (contact in contacts.documents) {
            cache[contact.id] = contact
        }

        return contacts
    }
}