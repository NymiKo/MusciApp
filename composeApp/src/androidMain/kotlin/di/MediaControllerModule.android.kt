package di

import audio_player.AndroidAudioPlayerController
import audio_player.AudioPlayerController
import data.media.MediaRepository
import data.media.MediaRepositoryImpl
import ui.player.MediaViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val mediaControllerModule: Module = module {
    single<AudioPlayerController> { AndroidAudioPlayerController(get()) }
}