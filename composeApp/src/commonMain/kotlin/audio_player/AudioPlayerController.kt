package audio_player

import kotlinx.coroutines.flow.Flow

interface AudioPlayerController {
    fun prepare(url: String, listener: AudioPlayerListener)
    fun start()
    fun pause()
    fun stop()
    fun isPlaying(): Boolean
    fun getFullTime(): Long
    fun release()
    suspend fun getCurrentTime(): Flow<Long>
}