package data.home

import data.model.Artist
import data.model.Song

interface HomeRepository {
    suspend fun getLastSongsList(): List<Song>
    suspend fun getArtistsList(): List<Artist>
    suspend fun getSongsMyWave(lastId: Long): List<Song>
}