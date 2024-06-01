package ui.usecase

import audio_player.AudioPlayerController

class PauseSongUseCase(private val audioPlayerController: AudioPlayerController) {
    fun pause() {
        audioPlayerController.pause()
    }
}