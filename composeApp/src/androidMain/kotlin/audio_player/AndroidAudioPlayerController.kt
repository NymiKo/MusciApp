package audio_player

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import data.model.Song

class AndroidAudioPlayerController(context: Context) : AudioPlayerController {


    private val controllerFeature: ListenableFuture<MediaController>
    private val mediaPlayer: MediaController? get() = if (controllerFeature.isDone && !controllerFeature.isCancelled) controllerFeature.get() else null

    override var audioControllerCallback: (
        (
            playerState: AudioPlayerState,
            currentPosition: Int,
            currentTime: Long,
            totalTime: Long,
            isShuffle: Boolean,
            isRepeat: Boolean
        ) -> Unit
    )? = null

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, MediaService::class.java))
        controllerFeature = MediaController.Builder(context, sessionToken).buildAsync()
        controllerFeature.addListener({ setController() }, MoreExecutors.directExecutor())
    }

    private fun setController() {
        mediaPlayer?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)

                with(player) {
                    audioControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        currentMediaItemIndex,
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L),
                        shuffleModeEnabled,
                        repeatMode == Player.REPEAT_MODE_ALL
                    )
                }
            }
        })
    }

    override fun addMediaItems(songs: List<Song>) {
        val mediaItems: List<MediaItem> = songs.map { song ->
            MediaItem.Builder()
                .setMediaId(song.urlMusic)
                .setUri(Uri.parse(song.urlMusic))
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setArtist(song.artist)
                        .setTitle(song.title)
                        .setArtworkUri(Uri.parse(song.urlImage))
                        .build()
                ).build()
        }

        mediaPlayer?.setMediaItems(mediaItems)
        mediaPlayer?.prepare()
    }

    private fun Int.toPlayerState(isPlaying: Boolean): AudioPlayerState {
        return when(this) {
            Player.STATE_IDLE -> AudioPlayerState.STOPPED
            Player.STATE_ENDED -> AudioPlayerState.STOPPED
            else -> if (isPlaying) AudioPlayerState.PLAYING else AudioPlayerState.PAUSED
        }
    }

    override fun play(indexSong: Int) {
        mediaPlayer?.apply {
            seekToDefaultPosition(indexSong)
            playWhenReady = true
        }
    }

    override fun resume() {
        mediaPlayer?.play()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun nextSong() {
        mediaPlayer?.seekToNextMediaItem()
        mediaPlayer?.playWhenReady = true
    }

    override fun prevSong() {
        mediaPlayer?.seekToPreviousMediaItem()
        mediaPlayer?.playWhenReady = true
    }

    override fun seekTo(time: Long) {
        mediaPlayer?.seekTo(time)
    }

    override fun changeShuffleMode() {
        val shuffleMode = mediaPlayer?.shuffleModeEnabled ?: false
        mediaPlayer?.shuffleModeEnabled = !shuffleMode
    }

    override fun changeRepeatMode() {
        val repeatMode = mediaPlayer?.repeatMode ?: Player.REPEAT_MODE_OFF
        if (repeatMode == Player.REPEAT_MODE_OFF) {
            mediaPlayer?.repeatMode = Player.REPEAT_MODE_ALL
        } else {
            mediaPlayer?.repeatMode = Player.REPEAT_MODE_OFF
        }
    }

    override fun getCurrentTime(): Long {
        return mediaPlayer?.currentPosition ?: 0
    }

    override fun release() {
        MediaController.releaseFuture(controllerFeature)
        audioControllerCallback = null
    }
}