package data.home

import data.model.Song

interface HomeRepository {
    suspend fun getLastSongList(): List<Song>
}