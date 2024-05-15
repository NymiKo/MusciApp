package di

import audio_player.AudioPlayerController
import audio_player.DesktopAudioPlayerController
import data.player.MediaRepository
import data.player.MediaRepositoryImpl
import ui.player.PlayerViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val mediaControllerModule: Module = module {
    single<MediaRepository> { MediaRepositoryImpl() }
    single<AudioPlayerController> { DesktopAudioPlayerController() }
    factory { PlayerViewModel(get(), get()) }
}