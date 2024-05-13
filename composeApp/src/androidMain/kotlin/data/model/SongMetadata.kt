package data.model

import androidx.media3.common.MediaItem

data class SongMetadata(
    val id: String,
    val title: String,
    val artist: String,
    val artwork: String,
    val urlMusic: String
) {
    companion object {
        fun toSongMetadata(mediaItem: MediaItem) = SongMetadata(
            id = mediaItem.mediaId,
            title = mediaItem.mediaMetadata.title.toString(),
            artist = mediaItem.mediaMetadata.artist.toString(),
            artwork = mediaItem.mediaMetadata.artworkUri.toString(),
            urlMusic = mediaItem.mediaId
        )
    }
}