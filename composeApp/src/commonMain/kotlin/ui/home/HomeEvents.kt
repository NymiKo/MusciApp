package ui.home

import data.model.Song

sealed class HomeEvents {
    object PlaySong : HomeEvents()
    object PauseSong : HomeEvents()
    object ResumeSong : HomeEvents()
    object FetchData : HomeEvents()
    object AddSongsToPlayer : HomeEvents()
    data class OnSongSelected(val selectedSong: Song) : HomeEvents()
}