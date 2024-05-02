package audio_player

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.annotation.OptIn
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.withContext
import org.easyprog.musicapp.extension.currentPositionFlow

class AndroidAudioPlayerController(context: Context) : AudioPlayerController {

    private var mediaPlayer = ExoPlayer.Builder(context).build()

    @OptIn(UnstableApi::class)
    override fun prepare(url: String, listener: AudioPlayerListener) {
        val mediaItem = MediaItem.fromUri(url)
        mediaPlayer.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when(playbackState) {
                    Player.STATE_READY -> listener.onReady()
                    Player.STATE_ENDED -> listener.onAudioFinished()
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
            }
        })

        mediaPlayer.setMediaItem(mediaItem)
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

    override suspend fun getCurrentTime(): Flow<Long> = withContext(Dispatchers.Main) {
        return@withContext mediaPlayer.currentPositionFlow()
    }
}