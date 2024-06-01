package ui.usecase

import audio_player.AudioPlayerController

class PlaySongUseCase(private val audioPlayerController: AudioPlayerController) {

    fun playSong(indexSong: Int) {
        audioPlayerController.play(indexSong)
    }
}