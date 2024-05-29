package di

import data.artist_songs.ArtistSongsRepository
import data.artist_songs.ArtistSongsRepositoryImpl
import data.home.HomeRepository
import data.home.HomeRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<ArtistSongsRepository> { ArtistSongsRepositoryImpl(get()) }
}