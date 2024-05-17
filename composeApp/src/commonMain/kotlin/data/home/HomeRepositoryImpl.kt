package data.home

import data.model.Artist
import data.model.Song
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val httpClient: HttpClient
): HomeRepository {
    override suspend fun getLastSongsList(): List<Song> = withContext(Dispatchers.IO) {
        val response = httpClient.get("http://f0862137.xsph.ru/musicApp/getNewSongs.php").body<List<Song>>()
        return@withContext response
    }

    override suspend fun getArtistsList(): List<Artist> = withContext(Dispatchers.IO) {
        val response = httpClient.get("http://f0862137.xsph.ru/musicApp/getArtistsForHome.php").body<List<Artist>>()
        return@withContext response
    }

    override suspend fun getSongsMyWave(lastId: Long): List<Song>  = withContext(Dispatchers.IO){
        val response = httpClient.get("http://f0862137.xsph.ru/musicApp/getSongs.php"){
            url {
                parameters.append("lastId", lastId.toString())
            }
        }.body<List<Song>>()
        return@withContext response
    }
}