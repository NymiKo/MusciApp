package di

import data.usecase.PauseUseCase
import data.usecase.ResumeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ResumeUseCase(get()) }
    single { PauseUseCase(get()) }
}