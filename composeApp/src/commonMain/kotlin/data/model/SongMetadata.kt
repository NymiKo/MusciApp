package data.model

data class SongMetadata(
    val id: Int,
    val title: String,
    val artist: String,
    val artwork: String,
    val duration: Long
)