package ui.artists_list

import data.model.Artist

data class ArtistsListScreenUiState(
    val loading: Boolean = true,
    val artistsList: List<Artist> = emptyList()
)
