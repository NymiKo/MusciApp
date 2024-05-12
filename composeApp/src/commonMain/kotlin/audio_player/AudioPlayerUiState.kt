package audio_player

data class AudioPlayerUiState(
    val playerState: AudioPlayerState = AudioPlayerState.STOPPED,
    val currentPosition: Int = 0,
    val currentTime: Long = 0L,
    val totalTime: Long = 0L,
    val isShuffle: Boolean = false,
    val isRepeat: Boolean = false
)
