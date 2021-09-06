package com.example.ishop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ishop.database.GroceryItemListDatabaseDao

class ShoppingViewModelFactory (
    private val dataSource: GroceryItemListDatabaseDao,
    private val listName: String,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ShoppingViewModel::class.java)) {
                return ShoppingViewModel(dataSource, listName, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}