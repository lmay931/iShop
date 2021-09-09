//package com.example.ishop
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.Transformations
//import com.example.ishop.formatItems
//import com.example.ishop.database.GroceryItem
//import com.example.ishop.database.GroceryItemListDatabaseDao
//import kotlinx.coroutines.*
//
//class NewListViewModel (
//    private val database: GroceryItemListDatabaseDao,
//    nameList : String,
//    application: Application
//) : AndroidViewModel(application) {
//
//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
//
//    private var _showButtons = MutableLiveData<Boolean>()
//
//    var listName = nameList
//    val showButtons: LiveData<Boolean>
//        get() = _showButtons
//
//    private var _showSnackBarEvent = MutableLiveData<Boolean>()
//
//    val showSnackBarEvent: LiveData<Boolean>
//        get() = _showSnackBarEvent
//
//    fun doneShowingSnackBar() {
//        _showSnackBarEvent.value = false
//    }
//    var fruitVegList = database.get(nameList, "fruit")
//    var dairyString = database.get(nameList,"dairy")
//    var meatString = database.get(nameList,"meat")
//    var seafoodString = database.get(nameList,"seafood")
//    var alcoholString = database.get(nameList,"alcohol")
//
//    private val _navigateToShopping = MutableLiveData<String>()
//    val navigateToShopping: LiveData<String>
//        get() = _navigateToShopping
//
//    fun setNavigateToShopping() {
//        _navigateToShopping.value = listName
//    }
//
//    private suspend fun getAllLists(): List<String> {
//        return withContext(Dispatchers.IO) {
//            return@withContext database.getLists()
//        }
//    }
//
//    private suspend fun update(item: GroceryItem) {
//        withContext(Dispatchers.IO) {
//            database.update(item)
//        }
//    }
//
//    private suspend fun insert(item: GroceryItem) {
//        withContext(Dispatchers.IO){
//            database.insert(item)
//        }
//    }
//
//    fun addItem(cat: String, item: String) {
//        uiScope.launch {
//            val newItem = GroceryItem()
//            newItem.Category = cat
//            newItem.Item = item
//            newItem.ListName = listName
//            insert(newItem)
//        }
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }
//}