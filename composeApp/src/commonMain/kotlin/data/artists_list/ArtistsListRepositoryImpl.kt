package data.artists_list

import data.model.Artist
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistsListRepositoryImpl(
    private val httpClient: HttpClient
): ArtistsListRepository {
    override suspend fun getArtistsList(): List<Artist> = withContext(Dispatchers.IO){
        val response = httpClient.get("http://f0862137.xsph.ru/musicApp/getArtistsList.php").body<List<Artist>>()
        return@withContext response
    }
}