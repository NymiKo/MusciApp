package data.usecase

import audio_player.AudioPlayerController

class ResumeUseCase(private val audioPlayerController: AudioPlayerController) {
    fun resume() {
        audioPlayerController.resume()
    }
}