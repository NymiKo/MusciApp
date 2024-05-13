package audio_player

import data.model.Song

data class AudioPlayerUiState(
    val playerState: AudioPlayerState = AudioPlayerState.STOPPED,
    val songList: List<Song> = emptyList(),
    val currentPosition: Int = 0,
    val currentTime: Long = 0L,
    val totalTime: Long = 0L,
    val isShuffle: Boolean = false,
    val isRepeat: Boolean = false
)
