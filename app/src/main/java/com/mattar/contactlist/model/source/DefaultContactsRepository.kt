package com.mattar.contactlist.model.source

import androidx.lifecycle.LiveData
import com.mattar.contactlist.model.Contact
import com.mattar.contactlist.model.Result
import kotlinx.coroutines.*

class DefaultContactsRepository(
        private val contactsLocalDataSource: ContactsDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : ContactsRepository {
    override fun observeContact(): LiveData<Result<List<Contact>>> {
        return contactsLocalDataSource.observeContacts()
    }

    override suspend fun observeContactById(msisdn: String): Result<Contact> {
        return contactsLocalDataSource.observeContactById(msisdn)
    }

    override suspend fun saveContact(contact: Contact) {
        coroutineScope {
            launch { contactsLocalDataSource.saveContact(contact) }
        }
    }

    override suspend fun updateContact(contact: Contact) {
        coroutineScope {
            launch { contactsLocalDataSource.updateContact(contact) }
        }
    }

    override suspend fun deleteAllContacts() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch {
                    contactsLocalDataSource.deleteAllContacts()
                }
            }
        }
    }

    override suspend fun deleteContact(msisdn: String) {
        coroutineScope {
            launch { contactsLocalDataSource.deleteContact(msisdn) }
        }
    }
}