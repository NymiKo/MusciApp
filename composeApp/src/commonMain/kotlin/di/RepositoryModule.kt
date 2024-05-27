package di

import data.home.HomeRepository
import data.home.HomeRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }
}