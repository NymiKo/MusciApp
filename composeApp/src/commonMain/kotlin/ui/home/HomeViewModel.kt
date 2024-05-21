package ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import data.home.HomeRepository
import data.model.Song
import data.usecase.PauseUseCase
import data.usecase.ResumeUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    private val audioPlayerController: AudioPlayerController,
    private val resumeUseCase: ResumeUseCase,
    private val pauseUseCase: PauseUseCase
): ViewModel() {

    var homeScreenUiState by mutableStateOf(HomeScreenUiState())
        private set

    fun onEvent(events: HomeEvents) {
        when(events) {
            HomeEvents.FetchData -> fetchData()
            HomeEvents.PauseSong -> pauseUseCase.pause()
            is HomeEvents.PlaySong -> playSong(indexSong = events.indexSong)
            HomeEvents.ResumeSong -> resumeUseCase.resume()
            is HomeEvents.AddSongsToPlayer -> addSongsToPlayer(songsList = events.songsList)
        }
    }

    private fun fetchData() {
        homeScreenUiState = homeScreenUiState.copy(loading = true)
        getLastSongList()
        getArtists()
        homeScreenUiState = homeScreenUiState.copy(loading = false)
    }

    private fun getLastSongList() = viewModelScope.launch {
        val result = repository.getLastSongsList()
        homeScreenUiState = homeScreenUiState.copy(lastSongsList = result)
    }

    private fun addSongsToPlayer(songsList: List<Song>) {
        audioPlayerController.setMediaItems(songs = songsList)
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
}