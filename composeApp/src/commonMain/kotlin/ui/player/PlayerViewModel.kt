package ui.player

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import audio_player.AudioPlayerController
import ui.usecase.PauseSongUseCase
import ui.usecase.ResumeSongUseCase

class PlayerViewModel(
    private val audioPlayerController: AudioPlayerController,
    private val resumeSongUseCase: ResumeSongUseCase,
    private val pauseSongUseCase: PauseSongUseCase
) : ViewModel() {

    var playerUiState by mutableStateOf(PlayerScreenUiState())
        private set

    fun onEvent(events: PlayerEvents) {
        when(events) {
            PlayerEvents.ChangeRepeatMode -> changeRepeatMode()
            PlayerEvents.ChangeShuffleMode -> changeShuffleMode()
            is PlayerEvents.ChangeTime -> changeTime(time = events.time)
            PlayerEvents.NextSong -> nextSong()
            PlayerEvents.PauseSong -> pauseSongUseCase.pause()
            PlayerEvents.PrevSong -> prevSong()
            PlayerEvents.ResumeSong -> resumeSongUseCase.resume()
            is PlayerEvents.ScrollToSong -> scrollToSong(indexSong = events.indexSong)
        }
    }

    private fun changeTime(time: Float) {
        audioPlayerController.seekTo(time.toLong())
    }

    private fun nextSong() {
        audioPlayerController.nextSong()
    }

    private fun prevSong() {
        audioPlayerController.prevSong()
    }

    private fun scrollToSong(indexSong: Int) {
        audioPlayerController.play(indexSong)
    }

    private fun changeRepeatMode() {
        audioPlayerController.changeRepeatMode()
    }

    private fun changeShuffleMode() {
        audioPlayerController.changeShuffleMode()
    }
}