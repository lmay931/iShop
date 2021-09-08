package com.example.ishop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ishop.database.GroceryItemListDatabaseDao

class ManageCategoriesViewModelFactory (
    private val dataSource: GroceryItemListDatabaseDao,
    private val listName: String,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManageCategoriesViewModel::class.java)) {
                return ManageCategoriesViewModel(dataSource, listName, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}