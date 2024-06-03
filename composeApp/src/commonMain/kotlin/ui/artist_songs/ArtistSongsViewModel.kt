package ui.artist_songs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.artist_songs.ArtistSongsRepository
import kotlinx.coroutines.launch
import ui.usecase.PauseSongUseCase
import ui.usecase.PlaySongUseCase
import ui.usecase.ResumeSongUseCase

class ArtistSongsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: ArtistSongsRepository,
    private val playSongUseCase: PlaySongUseCase,
    private val resumeSongUseCase: ResumeSongUseCase,
    private val pauseSongUseCase: PauseSongUseCase
) : ViewModel() {
    var artistSongsUiState by mutableStateOf(
        ArtistSongsScreenUiState(
            idArtist = checkNotNull(savedStateHandle["idArtist"]),
            nameArtist = checkNotNull(savedStateHandle["nameArtist"])
        )
    )
        private set

    init {
        onEvent(ArtistSongsEvents.FetchArtistSongs)
    }

    fun onEvent(events: ArtistSongsEvents) {
        when(events) {
            ArtistSongsEvents.FetchArtistSongs -> fetchArtistSongs()
            is ArtistSongsEvents.PlaySong -> playSongUseCase.playSong(events.indexSong)
            ArtistSongsEvents.PauseSong -> pauseSongUseCase.pause()
            ArtistSongsEvents.ResumeSong -> resumeSongUseCase.resume()
        }
    }

    private fun fetchArtistSongs() = viewModelScope.launch{
        artistSongsUiState = artistSongsUiState.copy(loading = true)
        val result = repository.getArtistSongs(artistSongsUiState.idArtist)
        artistSongsUiState = artistSongsUiState.copy(loading = false, imageArtist = result.imageArtist, songsList = result.songsList)
    }
}