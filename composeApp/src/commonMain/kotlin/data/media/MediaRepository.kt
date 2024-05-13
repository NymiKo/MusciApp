package data.media

import data.model.Song

interface MediaRepository {
    suspend fun getSongsList(): List<Song>
}