package audio_player

import data.model.Song
import data.model.SongMetadata

interface AudioPlayerController {
    var audioControllerCallback: (
        (
        playerState: AudioPlayerState,
        currentSong: SongMetadata,
        currentPosition: Int,
        currentTime: Long,
        totalTime: Long,
        isShuffle: Boolean,
        isRepeat: Boolean,
        mediaList: List<SongMetadata>
        ) -> Unit
    )?
    fun setMediaItems(songs: List<Song>)
    fun addMediaItems(songs: List<Song>)
    fun play(indexSong: Int)
    fun resume()
    fun pause()
    fun nextSong()
    fun prevSong()
    fun seekTo(time: Long)
    fun release()
    fun changeShuffleMode()
    fun changeRepeatMode()
    fun getCurrentTime(): Long
}