package screens

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

class AudioPlayer {
    private var mediaPlayer: MediaPlayer? = null

    private fun initMediaPlayer() {
        NativeDiscovery().discover()

        mediaPlayer = AudioPlayerComponent().mediaPlayer()

        mediaPlayer?.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) {
                super.mediaPlayerReady(mediaPlayer)
            }

            override fun finished(mediaPlayer: MediaPlayer?) {
                super.finished(mediaPlayer)
            }

            override fun error(mediaPlayer: MediaPlayer?) {
                super.error(mediaPlayer)
            }
        })
    }

    fun prepare() {
        if (mediaPlayer == null) {
            initMediaPlayer()
        }

        if (mediaPlayer?.status()?.isPlaying == true) {
            mediaPlayer?.controls()?.stop()
        }
    }

    fun play(url: String) {
        mediaPlayer?.media()?.play(url)
    }

    fun start() {
        mediaPlayer?.controls()?.play()
    }

    fun pause() {
        mediaPlayer?.controls()?.pause()
    }

    fun stop() {
        mediaPlayer?.controls()?.stop()
    }

    fun isPlaying() = mediaPlayer?.status()?.isPlaying ?: false

    fun release() {
        mediaPlayer?.release()
    }
}