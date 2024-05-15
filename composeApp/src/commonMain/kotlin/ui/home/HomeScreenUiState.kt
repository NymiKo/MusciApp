package ui.home

import data.model.Song

data class HomeScreenUiState(
    val lastSongsList: List<Song> = emptyList()
)