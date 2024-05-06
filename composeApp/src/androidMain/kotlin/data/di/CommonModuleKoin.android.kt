package data.di

import audio_player.AndroidAudioPlayerController
import audio_player.AudioPlayerController
import data.MediaRepository
import data.MediaRepositoryImpl
import data.MediaViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonModule: Module = module {
    single<MediaRepository> { MediaRepositoryImpl(get()) }
    single<AudioPlayerController> { AndroidAudioPlayerController(get()) }
    factory { MediaViewModel(get(), get()) }
}