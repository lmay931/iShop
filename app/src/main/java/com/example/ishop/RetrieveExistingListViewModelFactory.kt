package com.example.ishop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ishop.database.GroceryItemListDatabaseDao

class RetrieveExistingListViewModelFactory (
    private val dataSource: GroceryItemListDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RetrieveExistingListViewModel::class.java)) {
                return RetrieveExistingListViewModel(dataSource, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}