package shopping.list.ishop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import shopping.list.ishop.database.GroceryItem
import shopping.list.ishop.database.GroceryItemListDatabaseDao
import kotlinx.coroutines.*

class ShoppingViewModel (
    private val database: GroceryItemListDatabaseDao,
    val listName: String,
    application: Application
) : AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val _readyToNavigate = MutableLiveData<Boolean>()
    val readyToNavigate: LiveData<Boolean>
        get() = _readyToNavigate

    private var _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: MutableLiveData<Boolean>
        get() = _showSnackBarEvent

    fun setSnackBar(){
        _showSnackBarEvent.value = true
    }
    fun doneShowingSnackBar() {
        _showSnackBarEvent.value = false
    }

    fun changeSwitch(item : GroceryItem){
        uiScope.launch {
            if(update(item)){
                _readyToNavigate.value = true
            }
        }

    }

    fun resetSwitches(){
        uiScope.launch {
            resetSw()
        }
    }

    private suspend fun resetSw() {
        withContext(Dispatchers.IO){
            database.resetAllSwitches()
        }
    }

    private suspend fun update(item: GroceryItem) : Boolean {
        var switches : Boolean
        withContext(Dispatchers.IO){
            database.update(item)
            val switchList = database.checkSwitches(listName)
            switches = !(switchList.contains(false))
        }
        return switches
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}