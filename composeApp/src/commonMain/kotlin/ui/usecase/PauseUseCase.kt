package ui.usecase

import audio_player.AudioPlayerController

class PauseUseCase(private val audioPlayerController: AudioPlayerController) {
    fun pause() {
        audioPlayerController.pause()
    }
}