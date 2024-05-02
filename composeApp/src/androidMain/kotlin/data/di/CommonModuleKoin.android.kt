package data.di

import audio_player.AndroidAudioPlayerController
import audio_player.AudioPlayerController
import data.SongsRepository
import data.SongsRepositoryImpl
import data.SongsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonModule: Module = module {
    single<SongsRepository> { SongsRepositoryImpl(get()) }
    single<AudioPlayerController> { AndroidAudioPlayerController(get()) }
    factory { SongsViewModel(get(), get()) }
}