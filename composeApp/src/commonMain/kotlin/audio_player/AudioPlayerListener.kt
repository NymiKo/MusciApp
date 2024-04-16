package audio_player

interface AudioPlayerListener {
    fun onReady()
    fun timeChanged(newTime: Long)
    fun onAudioFinished()
    fun onError()
}