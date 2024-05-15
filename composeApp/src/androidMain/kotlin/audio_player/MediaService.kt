package audio_player

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import org.easyprog.musicapp.ui.MainActivity

class MediaService : MediaSessionService() {
    private var mediaSession: MediaSession? = null

    companion object {
        private val immutableFlag = PendingIntent.FLAG_IMMUTABLE
    }

    @OptIn(UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        val player = ExoPlayer.Builder(this)
            .setAudioAttributes(AudioAttributes.DEFAULT, true).build()
        player.addAnalyticsListener(EventLogger())

        mediaSession =
            MediaSession.Builder(this, player).setCallback(MediaSessionServiceCallback())
                .also { builder -> getSingleTopActivity()?.let { builder.setSessionActivity(it) } }.build()
    }

//    override fun onTaskRemoved(rootIntent: Intent?) {
//        val player = mediaSession?.player
//        if (!player.playWhenReady || player.mediaItemCount == 0) {
//            stopSelf()
//        }
//    }

    private fun getSingleTopActivity(): PendingIntent? {
        return PendingIntent.getActivity(
            this, 0, Intent(this, MainActivity::class.java), immutableFlag or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getBackStackedActivity(): PendingIntent? {
        return TaskStackBuilder.create(this).run {
            addNextIntent(Intent(this@MediaService, MainActivity::class.java))
            getPendingIntent(0, immutableFlag or PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }

    @OptIn(UnstableApi::class)
    override fun onDestroy() {
        mediaSession?.run {
            getBackStackedActivity()?.let { setSessionActivity(it) }
            player.release()
            release()
            mediaSession = null
        }

        super.onDestroy()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? =
        mediaSession

    private inner class MediaSessionServiceCallback : MediaSession.Callback {
        override fun onAddMediaItems(
            mediaSession: MediaSession,
            controller: MediaSession.ControllerInfo,
            mediaItems: MutableList<MediaItem>
        ): ListenableFuture<MutableList<MediaItem>> {
            val updatedMediaItems = mediaItems.map {
                it.buildUpon().setUri(it.mediaId).build()
            }.toMutableList()

            return Futures.immediateFuture(updatedMediaItems)
        }
    }
}