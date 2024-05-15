package data.player

import data.model.Song

interface MediaRepository {
    suspend fun getSongsList(): List<Song>
}