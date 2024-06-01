package di

import ui.usecase.PauseSongUseCase
import ui.usecase.ResumeSongUseCase
import org.koin.dsl.module
import ui.usecase.PlaySongUseCase

val useCaseModule = module {
    single { ResumeSongUseCase(get()) }
    single { PauseSongUseCase(get()) }
    single { PlaySongUseCase(get()) }
}