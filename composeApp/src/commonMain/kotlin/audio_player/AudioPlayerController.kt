package audio_player

expect class AudioPlayerController() {
    fun prepare(url: String, listener: AudioPlayerListener)
    fun start()
    fun pause()
    fun stop()
    fun isPlaying(): Boolean
    fun getFullTime(): Long
    fun release()
}