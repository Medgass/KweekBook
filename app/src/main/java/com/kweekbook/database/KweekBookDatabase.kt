package com.kweekbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kweekbook.model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class KweekBookDatabase : RoomDatabase() {
    
    abstract fun bookDao(): BookDao
    
    companion object {
        @Volatile
        private var INSTANCE: KweekBookDatabase? = null
        
        fun getInstance(context: Context): KweekBookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KweekBookDatabase::class.java,
                    "kweekbook_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
