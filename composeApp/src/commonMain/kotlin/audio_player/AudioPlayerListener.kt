package audio_player

interface AudioPlayerListener {
    fun onReady()
    fun onAudioFinished()
    fun onError()
}