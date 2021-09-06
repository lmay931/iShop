package com.example.ishop.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list_table")
data class GroceryItem(

    @PrimaryKey(autoGenerate = true)
    var ItemId: Long = 0L,

//    @ColumnInfo(name = "listId")
//    var ListId: Long = 0L,

    @ColumnInfo(name = "list_name")
    var ListName: String = "DefaultName",

    @ColumnInfo(name = "grocery_category")
    var Category: String = "Default",

    @ColumnInfo(name = "grocery_item")
    var Item: String = "Item_New"
)