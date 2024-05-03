package audio_player

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.easyprog.musicapp.extension.currentPositionFlow

class AndroidAudioPlayerController(context: Context) : AudioPlayerController {

    private var mediaPlayer = ExoPlayer.Builder(context).build()

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
        mediaPlayer.release()
    }

    override fun nextSong() {
        if (mediaPlayer.hasNextMediaItem()) {
            mediaPlayer.seekToNextMediaItem()
        } else {
            mediaPlayer.seekToDefaultPosition(0)
        }
    }

    override fun prevSong() {
        if (mediaPlayer.hasPreviousMediaItem()) {
            mediaPlayer.seekToPreviousMediaItem()
        } else {
            mediaPlayer.seekToDefaultPosition(0)
        }
    }

    override suspend fun getCurrentTime(): Flow<Long> = withContext(Dispatchers.Main) {
        return@withContext mediaPlayer.currentPositionFlow()
    }
}