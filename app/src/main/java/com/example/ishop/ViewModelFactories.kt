package com.example.ishop

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ishop.database.GroceryItemListDatabaseDao

class ChooseNewNameViewModelFactory (
    private val dataSource: GroceryItemListDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseNewNameViewModel::class.java)) {
            return ChooseNewNameViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

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

class ManageListsViewModelFactory (
    private val dataSource: GroceryItemListDatabaseDao,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManageListsViewModel::class.java)) {
            return ManageListsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class AddItemsViewModelFactory (
    private val dataSource: GroceryItemListDatabaseDao,
    private val listName: String,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemsViewModel::class.java)) {
            return AddItemsViewModel(dataSource, listName, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

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