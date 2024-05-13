package di

import org.koin.core.module.Module
import org.koin.dsl.module
import ui.SharedViewModel
import ui.player.MediaViewModel

val viewModelModule: Module = module {
    factory { MediaViewModel(get(), get()) }
    factory { SharedViewModel(get()) }
}