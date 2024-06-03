package ui.artist_songs

sealed class ArtistSongsEvents {
    object FetchArtistSongs : ArtistSongsEvents()
    data class PlaySong(val indexSong: Int) : ArtistSongsEvents()
    object ResumeSong : ArtistSongsEvents()
    object PauseSong : ArtistSongsEvents()
}