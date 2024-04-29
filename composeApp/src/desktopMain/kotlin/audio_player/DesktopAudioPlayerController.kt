package audio_player

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

class DesktopAudioPlayerController() : AudioPlayerController {
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

    override fun prepare(url: String, listener: AudioPlayerListener) {
        if (audioPlayer == null) {
            initMediaPlayer()
            this.listener = listener
        }

        if (audioPlayer?.status()?.isPlaying == true) {
            audioPlayer?.controls()?.stop()
        }
        audioPlayer?.media()?.play(url)
    }

    override fun start() {
        audioPlayer?.controls()?.play()
    }

    override fun pause() {
        audioPlayer?.controls()?.pause()
    }

    override fun stop() {
        audioPlayer?.controls()?.stop()
    }

    override fun isPlaying(): Boolean {
        return audioPlayer?.status()?.isPlaying ?: false
    }

    override fun release() {
        audioPlayer?.release()
    }

    override fun getFullTime(): Long {
        return audioPlayer?.status()?.length() ?: 0
    }
}