package ui.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import data.media.MediaRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MediaViewModel(
    private val repository: MediaRepository,
    private val audioPlayerController: AudioPlayerController
) : ViewModel() {

    var audioPlayerUiState by mutableStateOf(AudioPlayerUiState())
        private set

    init {
        loadSongsList()
        playSong()
    }

    private fun loadSongsList() {
        viewModelScope.launch {
            val result = repository.getSongsList()
            audioPlayerController.addMediaItems(result)
            audioPlayerUiState = audioPlayerUiState.copy(songList = result)
        }
    }

    fun playSong() {
        audioControllerCallback { playerState, currentPosition, currentTime, totalTime, isShuffle, isRepeat ->
            audioPlayerUiState = audioPlayerUiState.copy(
                playerState = playerState,
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
            currentPosition: Int,
            currentTime: Long,
            totalTime: Long,
            isShuffle: Boolean,
            isRepeat: Boolean
        ) -> Unit
    ) {
        audioPlayerController.audioControllerCallback = callback
    }

    fun pauseOrPlay() {
        if (audioPlayerUiState.playerState == AudioPlayerState.PLAYING) {
            audioPlayerController.pause()
            audioPlayerUiState = audioPlayerUiState.copy(playerState = AudioPlayerState.PAUSED)
        } else {
            audioPlayerController.resume()
            audioPlayerUiState = audioPlayerUiState.copy(playerState = AudioPlayerState.PLAYING)
        }
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
        audioPlayerUiState = audioPlayerUiState.copy(currentPosition = indexSong)
        audioPlayerController.play(indexSong)
    }

    fun changeRepeatMode() {
        audioPlayerController.changeRepeatMode()
    }

    fun changeShuffleMode() {
        audioPlayerController.changeShuffleMode()
    }

    fun releasePlayer() {
        audioPlayerController.release()
    }
}