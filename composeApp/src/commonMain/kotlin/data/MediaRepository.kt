package data

import data.model.Song

interface MediaRepository {
    suspend fun getSongsList(): List<Song>
}