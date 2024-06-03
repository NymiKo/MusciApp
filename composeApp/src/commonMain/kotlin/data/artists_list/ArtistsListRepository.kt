package data.artists_list

import data.model.Artist

interface ArtistsListRepository {
    suspend fun getArtistsList(): List<Artist>
}