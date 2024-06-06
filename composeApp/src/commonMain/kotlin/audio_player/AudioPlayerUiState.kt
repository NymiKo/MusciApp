package audio_player

import data.model.Song
import data.model.SongMetadata

data class AudioPlayerUiState(
    val playerState: AudioPlayerState = AudioPlayerState.STOPPED,
    val currentSongsList: List<Song> = emptyList(),
    val mediaList: List<SongMetadata> = emptyList(),
    val currentSong: SongMetadata? = null,
    val currentPosition: Int = 0,
    val currentTime: Long = 0L,
    val totalTime: Long = 0L,
    val isShuffle: Boolean = false,
    val isRepeat: Boolean = false,
    val endGetSongs: Boolean = false,
    val myWaveMode: Boolean = false
)
