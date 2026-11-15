package com.deepudinesh.tusmindspace.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Quote::class], version = 2, exportSchema = false)
abstract class TUSMindSpaceDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao

    companion object {
        @Volatile
        private var Instance: TUSMindSpaceDatabase? = null

        fun getDatabase(context: Context): TUSMindSpaceDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, TUSMindSpaceDatabase::class.java, "tusmindspace_database")
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}