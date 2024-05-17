package ui.home

import data.model.Artist
import data.model.Song
import data.model.SongMetadata

data class HomeScreenUiState(
    val loading: Boolean = false,
    val lastSongsList: List<Song> = emptyList(),
    val selectedSong: Song? = null,
    val artistsList:  List<Artist> = emptyList()
)