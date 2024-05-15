package di

import data.home.HomeRepository
import data.home.HomeRepositoryImpl
import data.player.MediaRepository
import data.player.MediaRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<MediaRepository> { MediaRepositoryImpl(get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}