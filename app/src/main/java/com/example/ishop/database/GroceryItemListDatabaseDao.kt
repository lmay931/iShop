package com.example.ishop.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface GroceryItemListDatabaseDao{

    @Insert
    fun insert(item: GroceryItem)

    @Update
    fun update(item: GroceryItem)

    @Query("SELECT * FROM shopping_list_table WHERE list_name= :key1 AND grocery_category= :key2")
    fun get(key1: String?, key2: String): LiveData<List<GroceryItem>>

    @Query("SELECT DISTINCT list_name FROM shopping_list_table")
    fun getLists(): List<String>

    @Query("SELECT DISTINCT list_name FROM shopping_list_table")
    fun getArrayNames(): Array<String>
}