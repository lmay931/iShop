package com.example.ishop.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GroceryItem::class], version = 3,  exportSchema = false)
abstract class GroceryItemListDatabase : RoomDatabase(){
    abstract val groceryItemDatabaseDao: GroceryItemListDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: GroceryItemListDatabase? = null

        fun getInstance(context: Context) : GroceryItemListDatabase{
            synchronized(this){
                var instance = INSTANCE

                if ( instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GroceryItemListDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }

        }
    }

}