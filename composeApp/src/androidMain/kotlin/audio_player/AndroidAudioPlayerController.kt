package audio_player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.easyprog.musicapp.extension.currentPositionFlow

class AndroidAudioPlayerController(context: Context) : AudioPlayerController {

    private var mediaPlayer = ExoPlayer.Builder(context).build()
    private var mediaSession: MediaSession? = MediaSession.Builder(context, mediaPlayer).build()

    @OptIn(UnstableApi::class)
    override fun prepare(urls: List<String>, listener: AudioPlayerListener) {
        val mediaItems: List<MediaItem> = urls.map { url -> MediaItem.fromUri(url) }
        mediaPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when(playbackState) {
                    Player.STATE_READY -> listener.onReady()
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                listener.onAudioChanged(indexSong = mediaPlayer.currentMediaItemIndex)
            }
        })

        mediaPlayer.setMediaItems(mediaItems)
        mediaPlayer.prepare()
    }

    override fun start() {
        mediaPlayer.play()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun getFullTime(): Long {
        return if (mediaPlayer.playbackState == Player.STATE_READY) {
            mediaPlayer.duration
        } else 0L
    }

    override fun release() {
        mediaSession.run {
            mediaPlayer.release()
            release()
            mediaSession = null
        }
    }

    override fun changeSong(indexSong: Int) {
        mediaPlayer.seekToDefaultPosition(indexSong)
    }

    override suspend fun getCurrentTime(): Flow<Long> = withContext(Dispatchers.Main) {
        return@withContext mediaPlayer.currentPositionFlow()
    }
}