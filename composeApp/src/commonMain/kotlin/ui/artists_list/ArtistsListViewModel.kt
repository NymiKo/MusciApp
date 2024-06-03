package ui.artists_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.artists_list.ArtistsListRepository
import kotlinx.coroutines.launch

class ArtistsListViewModel(
    private val repository: ArtistsListRepository
): ViewModel() {
    var artistsListScreenUiState by mutableStateOf(ArtistsListScreenUiState())
        private set

    init {
        onEvent(ArtistsListEvents.FetchData)
    }

    fun onEvent(events: ArtistsListEvents) {
        when(events) {
            is ArtistsListEvents.FetchData -> fetchArtistsList()
        }
    }

    private fun fetchArtistsList() = viewModelScope.launch {
        artistsListScreenUiState = artistsListScreenUiState.copy(loading = true)
        val result = repository.getArtistsList()
        artistsListScreenUiState = artistsListScreenUiState.copy(loading = false, artistsList = result)
    }
}