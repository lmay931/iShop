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

class AddItemsViewModel (
    private val database: GroceryItemListDatabaseDao,
    nameList : String,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _showButtons = MutableLiveData<Boolean>()

    var listName = nameList

    val showButtons: LiveData<Boolean>
        get() = _showButtons

    private var _showSnackBarEvent = MutableLiveData<Boolean>()

    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }
    fun setSnackBar() {
        _showSnackBarEvent.value = true
    }


    var fruitVegList = database.get(nameList, "fruit")




    private val _navigateToShopping = MutableLiveData<String>()
    val navigateToShopping: LiveData<String>
        get() = _navigateToShopping

    fun setNavigateToShopping() {
        _navigateToShopping.value = listName
    }

    private suspend fun insert(item: GroceryItem) {
        withContext(Dispatchers.IO){
            database.insert(item)
        }
    }

    fun addItem(cat: String, item: String) {
        uiScope.launch {
            val newItem = GroceryItem()
            newItem.Category = cat
            newItem.Item = item
            newItem.ListName = listName
            insert(newItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}