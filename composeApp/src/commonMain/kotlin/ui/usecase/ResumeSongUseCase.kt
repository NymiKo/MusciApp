package ui.usecase

import audio_player.AudioPlayerController

class ResumeSongUseCase(private val audioPlayerController: AudioPlayerController) {
    fun resume() {
        audioPlayerController.resume()
    }
}