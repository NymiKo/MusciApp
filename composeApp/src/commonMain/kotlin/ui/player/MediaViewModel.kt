package ui.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import data.player.MediaRepository
import kotlinx.coroutines.launch

class MediaViewModel(
    private val repository: MediaRepository,
    private val audioPlayerController: AudioPlayerController
) : ViewModel() {

    var playerUiState by mutableStateOf(PlayerScreenUiState())
        private set

    init {
        loadSongsList()
    }

    private fun loadSongsList() {
        playerUiState = playerUiState.copy(loading = true)
        viewModelScope.launch {
            val result = repository.getSongsList()
            audioPlayerController.addMediaItems(result)
            playerUiState = playerUiState.copy(loading = false, songList = result)
        }
    }

    fun play(indexSong: Int) {
        audioPlayerController.play(indexSong)
    }

    fun resume() {
        audioPlayerController.resume()
    }

    fun pause() {
        audioPlayerController.pause()
    }

    fun changeTime(time: Float) {
        audioPlayerController.seekTo(time.toLong())
    }

    fun nextSong() {
        audioPlayerController.nextSong()
    }

    fun prevSong() {
        audioPlayerController.prevSong()
    }

    fun scrollToSong(indexSong: Int) {
        audioPlayerController.play(indexSong)
    }

    fun changeRepeatMode() {
        audioPlayerController.changeRepeatMode()
    }

    fun changeShuffleMode() {
        audioPlayerController.changeShuffleMode()
    }
}