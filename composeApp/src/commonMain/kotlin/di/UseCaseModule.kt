package di

import ui.usecase.PauseUseCase
import ui.usecase.ResumeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ResumeUseCase(get()) }
    single { PauseUseCase(get()) }
}