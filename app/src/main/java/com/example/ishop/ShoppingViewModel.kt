package com.example.ishop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ishop.database.GroceryItemListDatabaseDao

class ShoppingViewModel (
    private val database: GroceryItemListDatabaseDao,
    private val listName: String,
    application: Application
) : AndroidViewModel(application) {
    var groceryItems = database.getAll(listName)
}