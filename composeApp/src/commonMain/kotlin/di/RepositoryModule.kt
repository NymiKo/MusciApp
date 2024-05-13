package di

import data.media.MediaRepository
import data.media.MediaRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<MediaRepository> { MediaRepositoryImpl(get()) }
}