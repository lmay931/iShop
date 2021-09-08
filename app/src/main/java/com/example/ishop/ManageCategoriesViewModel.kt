package com.example.ishop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.ishop.formatItems
import com.example.ishop.database.GroceryItem
import com.example.ishop.database.GroceryItemListDatabaseDao
import kotlinx.coroutines.*

class ManageCategoriesViewModel (
    private val database: GroceryItemListDatabaseDao,
    private val listName: String,
    application: Application
) : AndroidViewModel(application) {

    var groceryCategories = database.getLists()



}