package data.artist_songs

import data.artist_songs.model.ArtistSongsResponse
import data.model.Song

interface ArtistSongsRepository {
    suspend fun getArtistSongs(idArtist: Long): ArtistSongsResponse
}