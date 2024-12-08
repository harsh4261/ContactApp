package com.example.contactapp.Data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(Contact::class),
    version = 1,
    exportSchema = true
)
abstract class ContactAppDatabase : RoomDatabase() {

    abstract fun getDao() : ContactDao
}

