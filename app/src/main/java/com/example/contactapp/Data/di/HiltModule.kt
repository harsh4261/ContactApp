package com.example.contactapp.Data.di

import android.app.Application
import androidx.room.Room
import com.example.contactapp.Data.database.ContactAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

//    private val MIGRATION_1_2 = object : Migration(1, 2) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            // Add the new column with a default value (if needed)
//            database.execSQL("ALTER TABLE contact_table ADD COLUMN image BLOB")
//        }
//    }

    @Provides
    fun provideDatabase(application: Application): ContactAppDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            ContactAppDatabase::class.java,
            "ContactApp.db"
        ).build()
    }

    // .addMigrations(MIGRATION_1_2).build() => This is used to add migration to the database
    // .fallbackToDestructiveMigration().build() => This is used to add migration to the database but it will delete all the old data in the database tables.
    // .setJournalMode(RoomDatabase.JournalMode.TRUNCATE) => This is used to download the data of the tables from the database. But this will remove some securities layer from the database , which results that we are able to see the data and also download them.
}