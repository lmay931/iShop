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

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _liveCategories = MutableLiveData<List<String>>()
    val liveCategories: LiveData<List<String>>
        get() = _liveCategories

    var categories : List<String>? = listOf("")

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: MutableLiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    fun setSnackBar() {
        _showSnackBarEvent.value = true
    }
    fun getLists() {
        uiScope.launch {
            _liveCategories.value = getLiveCategories()
        }
    }

    fun addCategory(cat: String) {
        categories = categories?.plus(cat)
    }

    fun setCategories() {
        categories = _liveCategories.value
    }

    private suspend fun getLiveCategories(): List<String> {
        return withContext(Dispatchers.IO) {
            return@withContext database.getCategories()
        }
    }

    private val _navigateToNewList = MutableLiveData<String>()
    val navigateToNewList: LiveData<String>
        get() = _navigateToNewList

    fun setNavigateToNewList() {
        _navigateToNewList.value = listName
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}