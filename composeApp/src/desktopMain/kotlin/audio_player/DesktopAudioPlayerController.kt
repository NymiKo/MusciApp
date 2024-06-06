package audio_player

import data.model.Song
import data.model.SongMetadata
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.media.MediaRef
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent

class DesktopAudioPlayerController() : AudioPlayerController {
    private var mediaPlayer: MediaPlayer? = null

    private fun setController() {
        NativeDiscovery().discover()

        mediaPlayer = AudioPlayerComponent().mediaPlayer()

        mediaPlayer?.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {

        })

        mediaPlayer?.events()?.addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
            override fun timeChanged(mediaPlayer: MediaPlayer, newTime: Long) {
                super.timeChanged(mediaPlayer, newTime)
                updateState(mediaPlayer)
            }

            override fun positionChanged(mediaPlayer: MediaPlayer, newPosition: Float) {
                super.positionChanged(mediaPlayer, newPosition)
                updateState(mediaPlayer)
            }

            override fun mediaChanged(mediaPlayer: MediaPlayer, media: MediaRef?) {
                super.mediaChanged(mediaPlayer, media)
                updateState(mediaPlayer)
            }

            override fun playing(mediaPlayer: MediaPlayer) {
                super.playing(mediaPlayer)
                updateState(mediaPlayer)
            }

            override fun paused(mediaPlayer: MediaPlayer) {
                super.paused(mediaPlayer)
                updateState(mediaPlayer)
            }

            override fun stopped(mediaPlayer: MediaPlayer) {
                super.stopped(mediaPlayer)
                updateState(mediaPlayer)
            }

            override fun finished(mediaPlayer: MediaPlayer) {
                super.finished(mediaPlayer)
                updateState(mediaPlayer)
            }
        })
    }

    private fun updateState(mediaPlayer: MediaPlayer) {
        audioControllerCallback?.invoke(
            statePlaying(),
            SongMetadata(
                mediaId = "",
                title = "",
                artist = "",
                artwork = ""
            ),
            mediaPlayer.status().position().toInt(),
            mediaPlayer.status().time().coerceAtLeast(0L),
            mediaPlayer.media().info().duration(),
            false,
            false,
            getMediaList()
        )
    }

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

    private fun statePlaying(): AudioPlayerState {
        return when(mediaPlayer?.status()?.isPlaying) {
            true -> AudioPlayerState.PLAYING
            false -> AudioPlayerState.PAUSED
            else -> AudioPlayerState.STOPPED
        }
    }

    private fun getMediaList(): List<SongMetadata> {
        val mediaList = mediaPlayer?.media()?.info()?.audioTracks()
        val audioListMetadata = mutableListOf<SongMetadata>()

        if (mediaPlayer != null && mediaList!!.size > 0) {
            for (i in 0 until mediaList.size) {
                audioListMetadata.add(
                    SongMetadata(
                        mediaId = mediaList[i].id().toString(),
                        title = mediaList[i].codecName(),
                        artwork = "",
                        artist = ""
                    )
                )
            }
        }

        return audioListMetadata
    }

    override fun setMediaItems(songs: List<Song>) {
        mediaPlayer?.media()?.play(songs.first().urlMusic)
    }

    override fun addMediaItems(songs: List<Song>) {
        mediaPlayer?.media()?.play(songs.first().urlMusic)
    }

    override fun play(indexSong: Int) {
        //mediaPlayer?.controls()?.play()
    }

    override fun resume() {
        mediaPlayer?.controls()?.start()
    }

    override fun pause() {
        mediaPlayer?.controls()?.pause()
    }

    override fun nextSong() {

    }

    override fun prevSong() {

    }

    override fun seekTo(time: Long) {

    }

    override fun release() {
        mediaPlayer?.release()
    }

    override fun changeShuffleMode() {

    }

    override fun changeRepeatMode() {

    }

    override fun getCurrentTime(): Long {
        return mediaPlayer?.status()?.time()?.coerceAtLeast(0L) ?: 0L
    }
}