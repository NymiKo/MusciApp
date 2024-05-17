package ui.home

import data.model.Song

sealed class HomeEvents {
    data class PlaySong(val indexSong: Int) : HomeEvents()
    object PauseSong : HomeEvents()
    object ResumeSong : HomeEvents()
    object FetchData : HomeEvents()
    data class AddSongsToPlayer(val songsList: List<Song>) : HomeEvents()
    data class OnSongSelected(val selectedSong: Song) : HomeEvents()
}