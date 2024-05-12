package audio_player

import data.model.Song
import kotlinx.coroutines.flow.Flow

interface AudioPlayerController {
    var audioControllerCallback: (
        (
            playerState: AudioPlayerState,
            currentPosition: Int,
            currentTime: Long,
            totalTime: Long,
            isShuffle: Boolean,
            isRepeat: Boolean
        ) -> Unit
    )?
    fun addMediaItems(songs: List<Song>)
    fun play(indexSong: Int)
    fun resume()
    fun pause()
    fun nextSong()
    fun prevSong()
    fun seekTo(time: Long)
    fun release()
    fun getCurrentTime(): Long
}