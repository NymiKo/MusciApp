package data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

open class ViewModel {
    val job = SupervisorJob()
    protected var viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.IO + job)
}