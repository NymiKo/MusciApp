package data.player

import data.model.Song
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MediaApi {
    private val client = HttpClient()

    suspend fun getSongsList(): List<Song> {
        val response = client.get("http://f0862137.xsph.ru/musicApp/")
        return response.body()
    }
}