package data

import data.model.Song

interface SongsRepository {
    suspend fun getSongsList(): List<Song>
}