package com.weightwatchers.ww_exercise_02.model.source

import androidx.lifecycle.LiveData
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.model.Result
import kotlinx.coroutines.*

class DefaultContactsRepository(
        private val contactsLocalDataSource: ContactsDataSource,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : ContactsRepository {
    override fun observeContact(): LiveData<Result<List<Contact>>> {
        return contactsLocalDataSource.observeContacts()
    }

    override fun observeContactById(msisdn: String): LiveData<Result<Contact>> {
        return contactsLocalDataSource.observeContactById(msisdn)
    }

    override suspend fun saveContact(contact: Contact) {
        coroutineScope {
            launch { contactsLocalDataSource.saveContact(contact) }
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