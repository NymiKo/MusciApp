package audio_player

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.MoreExecutors
import data.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.easyprog.musicapp.extension.currentPositionFlow

class AndroidAudioPlayerController(context: Context) : AudioPlayerController {

    private val sessionToken =
        SessionToken(context, ComponentName(context, PlaybackService::class.java))
    private val controllerFeature = MediaController.Builder(context, sessionToken).buildAsync()
    private var mediaPlayer: MediaController? = null

    @OptIn(UnstableApi::class)
    override fun prepare(songs: List<Song>, listener: AudioPlayerListener) {
        controllerFeature.addListener({
            mediaPlayer = controllerFeature.get()
        }, MoreExecutors.directExecutor())

        mediaPlayer?.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                when (playbackState) {
                    Player.STATE_READY -> listener.onReady()
                }
            }

            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                listener.onError()
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
                listener.onAudioChanged(indexSong = mediaPlayer?.currentMediaItemIndex ?: 0)
            }

        })

        val mediaItems: List<MediaItem> = songs.mapIndexed { index, song ->
            MediaItem.Builder()
                .setMediaId("media-$index")
                .setUri(Uri.parse(song.urlMusic))
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtist(song.artist)
                        .setTitle(song.title)
                        .setArtworkUri(Uri.parse(song.urlImage))
                        .build()
                ).build()
        }

        mediaPlayer?.playWhenReady = false
        mediaPlayer?.setMediaItems(mediaItems)
        mediaPlayer?.prepare()
    }

    override fun start() {
        mediaPlayer?.play()
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
        return mediaPlayer?.duration?.coerceAtLeast(0L) ?: 0L
    }

    override fun release() {
        MediaController.releaseFuture(controllerFeature)
        mediaPlayer = null
    }

    override fun changeSong(indexSong: Int) {
        mediaPlayer?.seekToDefaultPosition(indexSong)
    }

    override suspend fun getCurrentTime(): Flow<Long> = withContext(Dispatchers.Main) {
        return@withContext mediaPlayer?.currentPositionFlow()!!
    }
}