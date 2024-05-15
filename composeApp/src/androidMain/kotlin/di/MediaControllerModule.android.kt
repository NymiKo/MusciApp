package di

import audio_player.AndroidAudioPlayerController
import audio_player.AudioPlayerController
import org.koin.core.module.Module
import org.koin.dsl.module

actual val mediaControllerModule: Module = module {
    single<AudioPlayerController> { AndroidAudioPlayerController(get()) }
}