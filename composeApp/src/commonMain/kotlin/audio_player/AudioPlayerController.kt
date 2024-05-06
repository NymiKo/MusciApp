package audio_player

import data.model.Song
import kotlinx.coroutines.flow.Flow

interface AudioPlayerController {
    fun prepare(songs: List<Song>, listener: AudioPlayerListener)
    fun start()
    fun pause()
    fun stop()
    fun isPlaying(): Boolean
    fun getFullTime(): Long
    fun release()
    fun changeSong(indexSong: Int)
    suspend fun getCurrentTime(): Flow<Long>
}