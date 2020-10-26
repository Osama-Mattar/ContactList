package com.mattar.contactlist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact @JvmOverloads constructor(
        @ColumnInfo(name = "name") var name: String = "",
        @PrimaryKey @ColumnInfo(name = "msisdn") var msisdn: String = "")