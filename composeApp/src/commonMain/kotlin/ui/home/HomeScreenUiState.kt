package ui.home

import data.model.Artist
import data.model.Song

data class HomeScreenUiState(
    val loading: Boolean = false,
    val lastSongsList: List<Song> = emptyList(),
    val artistsList:  List<Artist> = emptyList()
)