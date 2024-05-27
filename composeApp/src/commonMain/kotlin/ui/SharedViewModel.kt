package ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import data.home.HomeRepository
import data.model.Song
import data.model.SongMetadata
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SharedViewModel(
    private val repository: HomeRepository,
    private val audioPlayerController: AudioPlayerController
) : ViewModel() {
    var audioPlayerUiState by mutableStateOf(AudioPlayerUiState())
        private set

    init {
        setupMediaControllerCallback()
    }

    private fun setupMediaControllerCallback() {
        audioControllerCallback { playerState, currentSong, currentPosition, currentTime, totalTime, isShuffle, isRepeat ->
            audioPlayerUiState = audioPlayerUiState.copy(
                playerState = playerState,
                currentSong = currentSong,
                currentPosition = currentPosition,
                currentTime = currentTime,
                totalTime = totalTime,
                isShuffle = isShuffle,
                isRepeat = isRepeat
            )

            if (audioPlayerUiState.playerState == AudioPlayerState.PLAYING) {
                viewModelScope.launch {
                    while (true) {
                        delay(500)
                        audioPlayerUiState =
                            audioPlayerUiState.copy(currentTime = audioPlayerController.getCurrentTime())
                    }
                }
            }
        }
    }

    private fun audioControllerCallback(
        callback: (
            playerState: AudioPlayerState,
            currentSong: SongMetadata,
            currentPosition: Int,
            currentTime: Long,
            totalTime: Long,
            isShuffle: Boolean,
            isRepeat: Boolean
        ) -> Unit
    ) {
        audioPlayerController.audioControllerCallback = callback
    }

    fun releaseMediaPlayer() {
        audioPlayerController.release()
    }

    fun setSongsList(songsList: List<Song>) {
        audioPlayerUiState = audioPlayerUiState.copy(currentSongsList = songsList, myWaveMode = false)
        audioPlayerController.setMediaItems(songsList)
    }

    fun getSongs() = viewModelScope.launch {
        audioPlayerUiState = audioPlayerUiState.copy(myWaveMode = true)
        if ((audioPlayerUiState.currentPosition == audioPlayerUiState.currentSongsList.lastIndex || audioPlayerUiState.currentPosition == 0) && !audioPlayerUiState.endGetSongs && audioPlayerUiState.myWaveMode) {
            val result = repository.getSongsMyWave(audioPlayerUiState.currentPosition.toLong())
            if (result.isEmpty()) {
                audioPlayerUiState = audioPlayerUiState.copy(endGetSongs = true)
                return@launch
            }
            if (audioPlayerUiState.currentSongsList.isEmpty()) audioPlayerController.setMediaItems(result)
            else audioPlayerController.addMediaItems(result)
            val songsList = audioPlayerUiState.currentSongsList.toMutableList()
            songsList.addAll(result)
            audioPlayerUiState = audioPlayerUiState.copy(currentSongsList = songsList)
            audioPlayerController.play(audioPlayerUiState.currentPosition)
        }
    }
}