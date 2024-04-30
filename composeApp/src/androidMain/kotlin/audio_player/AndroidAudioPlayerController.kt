package audio_player

import android.media.AudioAttributes
import android.media.MediaPlayer

class AndroidAudioPlayerController : AudioPlayerController {

    private var mediaPlayer: MediaPlayer? = null

    private fun initMediaPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
    }

    override fun prepare(url: String, listener: AudioPlayerListener) {
        if (mediaPlayer == null) {
            initMediaPlayer()
        }

        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepareAsync()
    }

    override fun start() {
        mediaPlayer?.start()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun stop() {
        mediaPlayer?.stop()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    override fun getFullTime(): Long {
        return mediaPlayer?.duration?.toLong() ?: 0L
    }

    override fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}