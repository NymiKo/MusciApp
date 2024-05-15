package ui.home

import data.model.Artist
import data.model.Song

data class HomeScreenUiState(
    val loading: Boolean = false,
    val lastSongsList: List<Song> = emptyList(),
    val selectedSong: Song? = null,
    val artistsList:  List<Artist> = emptyList()
)