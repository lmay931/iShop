package shopping.list.ishop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import shopping.list.ishop.database.GroceryItemListDatabaseDao
import kotlinx.coroutines.*

class ManageListsViewModel (
    private val database: GroceryItemListDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _nameList = MutableLiveData<List<String>>()
    val nameList: LiveData<List<String>>
        get() = _nameList

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: MutableLiveData<Boolean>
        get() = _showSnackBarEvent

    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    fun setSnackBar() {
        _showSnackBarEvent.value = true
    }

    fun deleteALL(): Unit? {
        uiScope.launch {
            clearALL()
        }
        return null
    }

    fun remove(selectedList: String) {
        uiScope.launch {
            delete(selectedList)
        }
    }

    fun getLists() {
        uiScope.launch {
            _nameList.value = getAllLists()
        }
    }

    private suspend fun delete(selectedList: String) {
        withContext(Dispatchers.IO) {
            database.clear(selectedList)
        }
    }

    private suspend fun clearALL() {
        withContext(Dispatchers.IO) {
            database.clearAll()
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


