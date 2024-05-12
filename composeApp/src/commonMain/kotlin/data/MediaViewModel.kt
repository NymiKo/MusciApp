package data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import data.model.Song
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MediaViewModel(
    private val repository: MediaRepository,
    private val audioPlayerController: AudioPlayerController
) : ViewModel() {

    var audioPlayerUiState by mutableStateOf(AudioPlayerUiState())
        private set

    private val _songsListFLow = MutableStateFlow<List<Song>>(
        emptyList()
    )
    val songsListFLow: StateFlow<List<Song>> get() = _songsListFLow

    init {
        loadSongsList()
    }

    private fun loadSongsList() {
        viewModelScope.launch {
            val result = repository.getSongsList()
            _songsListFLow.value = result
            audioPlayerController.addMediaItems(result)
            playSong()
        }
    }

    private fun playSong() {
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
        audioPlayerController.play(audioPlayerUiState.currentPosition)
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
        if (audioPlayerUiState.currentPosition == _songsListFLow.value.lastIndex) {
            audioPlayerUiState = audioPlayerUiState.copy(currentPosition = 0)
        } else {
            val nextSongIndex = audioPlayerUiState.currentPosition.plus(1)
            audioPlayerUiState = audioPlayerUiState.copy(currentPosition = nextSongIndex)
            audioPlayerController.play(audioPlayerUiState.currentPosition)
        }
    }

    fun prevSong() {
        if (audioPlayerUiState.currentPosition == 0) {
            audioPlayerUiState = audioPlayerUiState.copy(currentPosition = 0)
        } else {
            val prevSongIndex = audioPlayerUiState.currentPosition.minus(1)
            audioPlayerUiState = audioPlayerUiState.copy(currentPosition = prevSongIndex)
            audioPlayerController.prevSong()
        }
    }

    fun scrollToSong(indexSong: Int) {
        audioPlayerUiState = audioPlayerUiState.copy(currentPosition = indexSong)
        audioPlayerController.play(indexSong)
    }

    fun releasePlayer() {
        audioPlayerController.release()
    }
}