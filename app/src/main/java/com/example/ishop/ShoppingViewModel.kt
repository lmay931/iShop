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

class ShoppingViewModel (
    private val database: GroceryItemListDatabaseDao,
    private val listName: String,
    application: Application
) : AndroidViewModel(application) {

    var fruitVegString = Transformations.map(database.get(listName, "fruit")) {
            allItems -> formatItems(allItems)
    }
    var dairyString = Transformations.map(database.get(listName,"dairy")) {
            allItems -> formatItems(allItems)
    }
    var meatString = Transformations.map(database.get(listName,"meat")) {
            allItems -> formatItems(allItems)
    }
    var seafoodString = Transformations.map(database.get(listName,"seafood")) {
            allItems -> formatItems(allItems)
    }
    var alcoholString = Transformations.map(database.get(listName,"alcohol")) {
            allItems -> formatItems(allItems)
    }
}