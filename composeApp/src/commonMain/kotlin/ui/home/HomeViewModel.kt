package ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.home.HomeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
): ViewModel() {

    var homeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set

    init {
        getLastSongList()
    }

    private fun getLastSongList() {
        viewModelScope.launch {
            val result = repository.getLastSongList()
            homeScreenUiState = homeScreenUiState.copy(lastSongsList = result)
        }
    }
}