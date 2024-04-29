package data.di

import audio_player.AudioPlayerController
import audio_player.DesktopAudioPlayerController
import data.SongsRepository
import data.SongsRepositoryImpl
import data.SongsViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val commonModule: Module = module {
    single<SongsRepository> { SongsRepositoryImpl() }
    single<AudioPlayerController> { DesktopAudioPlayerController() }
    factory { SongsViewModel(get(), get()) }
}