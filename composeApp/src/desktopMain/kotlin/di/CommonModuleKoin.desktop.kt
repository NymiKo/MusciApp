package di

import audio_player.AudioPlayerController
import audio_player.DesktopAudioPlayerController
import data.media.MediaRepository
import data.media.MediaRepositoryImpl
import ui.player.MediaViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val mediaControllerModule: Module = module {
    single<MediaRepository> { MediaRepositoryImpl() }
    single<AudioPlayerController> { DesktopAudioPlayerController() }
    factory { MediaViewModel(get(), get()) }
}