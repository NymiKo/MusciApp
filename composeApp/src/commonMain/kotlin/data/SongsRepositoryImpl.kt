package data

import data.model.Song
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SongsRepositoryImpl(
    private val httpClient: HttpClient
): SongsRepository {
    override suspend fun getSongsList(): List<Song> = withContext(Dispatchers.IO) {
        val response = httpClient.get("http://f0862137.xsph.ru/musicApp/getSongs.php").body<List<Song>>()
        return@withContext response.shuffled()
    }
}