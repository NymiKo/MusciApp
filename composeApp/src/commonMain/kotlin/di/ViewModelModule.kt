package di

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.Module
import org.koin.dsl.module
import ui.SharedViewModel
import ui.artist_songs.ArtistSongsViewModel
import ui.artists_list.ArtistsListViewModel
import ui.home.HomeViewModel
import ui.player.PlayerViewModel

val viewModelModule: Module = module {
    factory { PlayerViewModel(get(), get(), get()) }
    factory { SharedViewModel(get(), get()) }
    factory { HomeViewModel(get(), get(), get(), get()) }
    factory { ArtistSongsViewModel(get(), get(), get(), get(), get()) }
    factory { ArtistsListViewModel(get()) }
}