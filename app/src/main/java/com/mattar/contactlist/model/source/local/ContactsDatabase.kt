package com.mattar.contactlist.model.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mattar.contactlist.model.Contact

/**
 * The Room Database that contains the Contact table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactsDao

}