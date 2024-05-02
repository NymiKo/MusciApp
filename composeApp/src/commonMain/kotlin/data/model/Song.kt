package data.model

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val title: String,
    val artist: String,
    val urlMusic: String,
    val urlImage: String
)
