package data.artist_songs

import data.artist_songs.model.ArtistSongsResponse
import data.model.Song
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistSongsRepositoryImpl(
    private val httpClient: HttpClient
): ArtistSongsRepository {
    override suspend fun getArtistSongs(idArtist: Long): ArtistSongsResponse = withContext(Dispatchers.IO) {
        val response: ArtistSongsResponse = httpClient.get("http://f0862137.xsph.ru/musicApp/getArtistSongs.php"){
            url {
                parameters.append("idArtist", idArtist.toString())
            }
        }.body()
        return@withContext response
    }
}