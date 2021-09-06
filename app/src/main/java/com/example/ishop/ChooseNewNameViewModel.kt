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

class ChooseNewNameViewModel (
    private val database: GroceryItemListDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _nameList = MutableLiveData<String>()
    val nameList: LiveData<String>
        get() = _nameList

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    fun setNameList(name : String){
        uiScope.launch {
            val list = getAllLists()
            if(name in list) {
                _showSnackBarEvent.value = true
            }
            else {
                _nameList.value = name
                }
            }
        }

    private suspend fun getAllLists(): List<String> {
        return withContext(Dispatchers.IO) {
            return@withContext database.getLists()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    }


