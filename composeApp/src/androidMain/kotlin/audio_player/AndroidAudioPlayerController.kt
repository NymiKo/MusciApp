package audio_player

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import data.model.Song
import data.model.SongMetadata

class AndroidAudioPlayerController(context: Context) : AudioPlayerController {
    private var controllerFuture: ListenableFuture<MediaController>
    private val mediaPlayer: MediaController? get() = if (controllerFuture.isDone && !controllerFuture.isCancelled) controllerFuture.get() else null

    override var audioControllerCallback: (
        (
        playerState: AudioPlayerState,
        currentSong: SongMetadata,
        currentPosition: Int,
        currentTime: Long,
        totalTime: Long,
        isShuffle: Boolean,
        isRepeat: Boolean,
        mediaList: List<SongMetadata>
    ) -> Unit
    )? = null

    init {
        val sessionToken =
            SessionToken(
                context.applicationContext,
                ComponentName(context.applicationContext, MediaService::class.java)
            )
        controllerFuture =
            MediaController.Builder(context.applicationContext, sessionToken).buildAsync()
        controllerFuture.addListener({ setController() }, MoreExecutors.directExecutor())
    }

    private fun setController() {
        mediaPlayer?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)

                with(player) {
                    audioControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        SongMetadata(
                            mediaId = currentMediaItem?.mediaId!!,
                            title = currentMediaItem?.mediaMetadata?.title.toString(),
                            artist = currentMediaItem?.mediaMetadata?.artist.toString(),
                            artwork = currentMediaItem?.mediaMetadata?.artworkUri.toString()
                        ),
                        currentMediaItemIndex,
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L),
                        shuffleModeEnabled,
                        repeatMode == Player.REPEAT_MODE_ALL,
                        getMediaList()
                    )
                }
            }
        })
    }

    private fun mapToMediaItems(songs: List<Song>): List<MediaItem> {
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

        return mediaItems
    }

    override fun setMediaItems(songs: List<Song>) {
        mediaPlayer?.clearMediaItems()
        mediaPlayer?.setMediaItems(mapToMediaItems(songs))
    }

    override fun addMediaItems(songs: List<Song>) {
        mediaPlayer?.addMediaItems(mapToMediaItems(songs))
    }

    private fun Int.toPlayerState(isPlaying: Boolean): AudioPlayerState {
        return when (this) {
            Player.STATE_IDLE -> AudioPlayerState.STOPPED
            Player.STATE_ENDED -> AudioPlayerState.STOPPED
            else -> if (isPlaying) AudioPlayerState.PLAYING else AudioPlayerState.PAUSED
        }
    }

    override fun play(indexSong: Int) {
        mediaPlayer?.apply {
            seekToDefaultPosition(indexSong)
            playWhenReady = true
            prepare()
        }
    }

    override fun resume() {
        mediaPlayer?.play()
    }

    override fun pause() {
        mediaPlayer?.pause()
    }

    override fun nextSong() {
        mediaPlayer?.seekToNext()
    }

    override fun prevSong() {
        mediaPlayer?.seekToPrevious()
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
        MediaController.releaseFuture(controllerFuture)
        audioControllerCallback = null
    }

    private fun getMediaList(): List<SongMetadata> {
        val mediaList = mutableListOf<SongMetadata>()
        if (mediaPlayer != null && mediaPlayer?.mediaItemCount!! > 0) {
            for (i in 0 until mediaPlayer!!.mediaItemCount) {
                val mediaItem = mediaPlayer!!.getMediaItemAt(i)
                mediaList.add(
                    SongMetadata(
                        mediaId = mediaItem.mediaId,
                        title = mediaItem.mediaMetadata.title.toString(),
                        artwork = mediaItem.mediaMetadata.artworkUri.toString(),
                        artist = mediaItem.mediaMetadata.artist.toString()
                    )
                )
            }
        }
        return mediaList
    }
}