package ui.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import data.player.MediaRepository
import data.usecase.PauseUseCase
import data.usecase.ResumeUseCase
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val repository: MediaRepository,
    private val audioPlayerController: AudioPlayerController,
    private val resumeUseCase: ResumeUseCase,
    private val pauseUseCase: PauseUseCase
) : ViewModel() {

    var playerUiState by mutableStateOf(PlayerScreenUiState())
        private set

    init {
        loadSongsList()
    }

    private fun loadSongsList() {
        viewModelScope.launch {
            playerUiState = playerUiState.copy(loading = false)
        }
    }

    fun resume() {
        resumeUseCase.resume()
    }

    fun pause() {
        pauseUseCase.pause()
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