package ui.artist_songs

import data.model.Song

data class ArtistSongsScreenUiState(
    val idArtist: Long = 0L,
    val nameArtist: String = "",
    val imageArtist: String = "",
    val songsList: List<Song> = emptyList()
)
