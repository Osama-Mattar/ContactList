package com.mattar.contactlist.model.source

import androidx.lifecycle.LiveData
import com.mattar.contactlist.model.Contact
import com.mattar.contactlist.model.Result

/**
 * Main entry point for accessing contacts data.
 */
interface ContactsDataSource {

    fun observeContacts(): LiveData<Result<List<Contact>>>

    suspend fun observeContactById(msisdn: String): Result<Contact>

    suspend fun saveContact(contact: Contact)

    suspend fun updateContact(contact: Contact): Int

    suspend fun deleteAllContacts()

    suspend fun deleteContact(msisdn: String)
}