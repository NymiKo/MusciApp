package di

import audio_player.AudioPlayerController
import audio_player.DesktopAudioPlayerController
import ui.player.PlayerViewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val mediaControllerModule: Module = module {
    single<AudioPlayerController> { DesktopAudioPlayerController() }
}