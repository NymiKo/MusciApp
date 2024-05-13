package ui.player

import data.model.Song

data class PlayerScreenUiState(
    val loading: Boolean = true,
    val songList: List<Song> = emptyList()
)
