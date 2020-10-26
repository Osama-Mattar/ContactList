package com.mattar.contactlist.model.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.mattar.contactlist.model.Contact

@Dao
interface ContactsDao {

    @Query("SELECT * FROM contacts")
    fun observeContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contacts WHERE msisdn=:msisdn")
    suspend fun observeContactById(msisdn: String): Contact

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact): Int

    @Query("DELETE FROM contacts WHERE msisdn = :msisdn")
    suspend fun deleteContactById(msisdn: String): Int

    @Query("DELETE FROM contacts")
    suspend fun deleteContacts()
}