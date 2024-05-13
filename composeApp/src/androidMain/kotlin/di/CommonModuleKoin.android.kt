package di

import audio_player.AndroidAudioPlayerController
import audio_player.AudioPlayerController
import data.media.MediaRepository
import data.media.MediaRepositoryImpl
import ui.player.MediaViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonModule: Module = module {
    single<MediaRepository> { MediaRepositoryImpl(get()) }
    single<AudioPlayerController> { AndroidAudioPlayerController(get()) }
    factory { MediaViewModel(get(), get()) }
}