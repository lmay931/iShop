package shopping.list.ishop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import shopping.list.ishop.database.GroceryItemListDatabaseDao
import kotlinx.coroutines.*

class RetrieveExistingListViewModel (
    private val database: GroceryItemListDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _nameList = MutableLiveData<Array<String>>()
    val nameList: LiveData<Array<String>>
        get() = _nameList

    private val _itemSelected = MutableLiveData<Boolean>()
    val itemSelected: LiveData<Boolean>
        get() = _itemSelected

    private val _listCategories = MutableLiveData<Array<String>>()
    val listCategories: LiveData<Array<String>>
        get() = _listCategories

    private suspend fun getAllLists(): Array<String> {
        return withContext(Dispatchers.IO) {
            return@withContext database.getArrayNames()
        }
    }
    private suspend fun getCategories(listSelected : String): Array<String> {
        return withContext(Dispatchers.IO) {
            return@withContext database.getCategoriesFromList(listSelected)
        }
    }

    fun setNameString() {
        uiScope.launch {
            _nameList.value = getAllLists()
        }
    }

    fun setCategories(listSelected : String) {
        uiScope.launch {
            _listCategories.value = getCategories(listSelected)
            _itemSelected.value = true
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}