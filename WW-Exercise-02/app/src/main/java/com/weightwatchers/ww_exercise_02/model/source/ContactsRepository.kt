package com.weightwatchers.ww_exercise_02.model.source

import androidx.lifecycle.LiveData
import com.weightwatchers.ww_exercise_02.model.Contact
import com.weightwatchers.ww_exercise_02.model.Result

interface ContactsRepository {

    fun observeContact(): LiveData<Result<List<Contact>>>

    fun observeContactById(msisdn: String): LiveData<Result<Contact>>

    suspend fun saveContact(contact: Contact)

    suspend fun deleteAllContacts()

    suspend fun deleteContact(msisdn: String)
}