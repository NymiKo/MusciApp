package ui.artist_songs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.artist_songs.ArtistSongsRepository
import kotlinx.coroutines.launch

class ArtistSongsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ArtistSongsRepository
) : ViewModel() {
    var artistSongsUiState by mutableStateOf(
        ArtistSongsScreenUiState(
            idArtist = checkNotNull(savedStateHandle["idArtist"]),
            nameArtist = checkNotNull(savedStateHandle["nameArtist"])
        )
    )
        private set

    init {
        onEvent(ArtistSongsEvents.fetchArtistSongs)
    }

    fun onEvent(events: ArtistSongsEvents) {
        when(events) {
            ArtistSongsEvents.fetchArtistSongs -> fetchArtistSongs()
        }
    }

    private fun fetchArtistSongs() = viewModelScope.launch{
        artistSongsUiState = artistSongsUiState.copy(loading = true)
        val result = repository.getArtistSongs(artistSongsUiState.idArtist)
        artistSongsUiState = artistSongsUiState.copy(loading = false, imageArtist = result.imageArtist, songsList = result.songsList)
    }
}