package ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import audio_player.AudioPlayerController
import data.home.HomeRepository
import data.model.Song
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    private val audioPlayerController: AudioPlayerController
): ViewModel() {

    var homeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set

    fun onEvent(events: HomeEvents) {
        when(events) {
            HomeEvents.FetchData -> {
                homeScreenUiState = homeScreenUiState.copy(loading = true)
                getLastSongList()
                getArtists()
                homeScreenUiState = homeScreenUiState.copy(loading = false)
            }
            is HomeEvents.OnSongSelected -> homeScreenUiState = homeScreenUiState.copy(selectedSong = events.selectedSong)
            HomeEvents.PauseSong -> pauseSong()
            is HomeEvents.PlaySong -> playSong(indexSong = events.indexSong)
            HomeEvents.ResumeSong -> resumeSong()
            is HomeEvents.AddSongsToPlayer -> addSongsToPlayer(songsList = events.songsList)
        }
    }

    private fun getLastSongList() = viewModelScope.launch {
        val result = repository.getLastSongsList()
        addSongsToPlayer(result)
        homeScreenUiState = homeScreenUiState.copy(lastSongsList = result)
    }

    private fun addSongsToPlayer(songsList: List<Song>) {
        audioPlayerController.addMediaItems(songs = songsList)
    }

    private fun getArtists() {
        viewModelScope.launch {
            val result = repository.getArtistsList()
            homeScreenUiState = homeScreenUiState.copy(artistsList = result)
        }
    }

    private fun playSong(indexSong: Int) {
        audioPlayerController.play(indexSong)
    }

    private fun resumeSong() {
        audioPlayerController.resume()
    }

    private fun pauseSong() {
        audioPlayerController.pause()
    }
}