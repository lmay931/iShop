package com.example.ishop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private var _showSnackBarEvent = MutableLiveData<Int?>()
    val showSnackBarEvent: MutableLiveData<Int?>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = null
    }

    fun setNameList(name : String){
        uiScope.launch {
            val list = getAllLists()
            when (name) {
                in list -> {
                    _showSnackBarEvent.value = 1
                }
                "" -> {
                    _showSnackBarEvent.value = 2
                }
                else -> {
                    _nameList.value = name
                }
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


