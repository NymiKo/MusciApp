package data.artist_songs.model

import data.model.Song
import kotlinx.serialization.Serializable

@Serializable
data class ArtistSongsResponse(
    val imageArtist: String,
    val songsList: List<Song>
)
