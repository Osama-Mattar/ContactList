package com.mattar.contactlist.model.source

import androidx.lifecycle.LiveData
import com.mattar.contactlist.model.Contact
import com.mattar.contactlist.model.Result

interface ContactsRepository {

    fun observeContact(): LiveData<Result<List<Contact>>>

    suspend fun observeContactById(msisdn: String): Result<Contact>

    suspend fun saveContact(contact: Contact)

    suspend fun updateContact(contact: Contact)

    suspend fun deleteAllContacts()

    suspend fun deleteContact(msisdn: String)
}