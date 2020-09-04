package com.weightwatchers.ww_exercise_02.model.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.model.Result
import com.weightwatchers.ww_exercise_02.model.Result.Success
import com.weightwatchers.ww_exercise_02.model.source.ContactsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactsLocalDataSource internal constructor(
        private val contactsDao: ContactsDao,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
    : ContactsDataSource {
    override fun observeContacts(): LiveData<Result<List<Contact>>> {
        return contactsDao.observeContacts().map {
            Success(it)
        }
    }

    override fun observeContactById(msisdn: String): LiveData<Result<Contact>> {
        return contactsDao.observeContactById(msisdn).map {
            Success(it)
        }
    }

    override suspend fun saveContact(contact: Contact) = withContext(ioDispatcher) {
        contactsDao.insertContact(contact)
    }

    override suspend fun deleteAllContacts() = withContext(ioDispatcher) {
        contactsDao.deleteContacts()
    }

    override suspend fun deleteContact(msisdn: String) = withContext<Unit>(ioDispatcher) {
        contactsDao.deleteContactById(msisdn)
    }
}