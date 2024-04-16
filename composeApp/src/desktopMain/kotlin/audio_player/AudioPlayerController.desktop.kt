package audio_player

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

actual class AudioPlayerController actual constructor() {
    private var audioPlayer: MediaPlayer? = null
    private var listener: AudioPlayerListener? = null

    private fun initMediaPlayer() {
        NativeDiscovery().discover()

        audioPlayer = AudioPlayerComponent().mediaPlayer()

        audioPlayer?.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) {
                super.mediaPlayerReady(mediaPlayer)
                listener?.onReady()
            }

            override fun timeChanged(mediaPlayer: MediaPlayer?, newTime: Long) {
                super.timeChanged(mediaPlayer, newTime)
                listener?.timeChanged(newTime = newTime)
            }

            override fun finished(mediaPlayer: MediaPlayer?) {
                super.finished(mediaPlayer)
                listener?.onAudioFinished()
            }

            override fun error(mediaPlayer: MediaPlayer?) {
                super.error(mediaPlayer)
                listener?.onError()
            }
        })
    }

    actual fun prepare(url: String, listener: AudioPlayerListener) {
        if (audioPlayer == null) {
            initMediaPlayer()
            this.listener = listener
        }

        if (audioPlayer?.status()?.isPlaying == true) {
            audioPlayer?.controls()?.stop()
        }
        audioPlayer?.media()?.play(url)
    }

    actual fun start() {
        audioPlayer?.controls()?.play()
    }

    actual fun pause() {
        audioPlayer?.controls()?.pause()
    }

    actual fun stop() {
        audioPlayer?.controls()?.stop()
    }

    actual fun isPlaying(): Boolean {
        return audioPlayer?.status()?.isPlaying ?: false
    }

    actual fun release() {
        audioPlayer?.release()
    }

    actual fun getFullTime(): Long {
        return audioPlayer?.status()?.length() ?: 0
    }
}