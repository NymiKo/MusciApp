package audio_player

class AndroidAudioPlayerController() : AudioPlayerController {
    override fun prepare(url: String, listener: AudioPlayerListener) {
    }

    override fun start() {
    }

    override fun pause() {
    }

    override fun stop() {
    }

    override fun isPlaying(): Boolean {
        return false
    }

    override fun getFullTime(): Long {
        return 3L
    }

    override fun release() {
    }
}