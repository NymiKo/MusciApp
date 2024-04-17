package data.di

import data.SongsApi
import data.SongsRepository
import data.SongsRepositoryImpl
import data.SongsViewModel
import org.koin.dsl.module

val commonModule = module {
    single<SongsRepository> { SongsRepositoryImpl() }
    factory { SongsViewModel(get()) }
}