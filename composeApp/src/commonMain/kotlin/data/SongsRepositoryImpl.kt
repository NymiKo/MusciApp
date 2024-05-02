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
        return@withContext response
    }

//    listOf(
//    Song(
//    "LE SSERAFIM",
//    "EASY",
//    "http://f0862137.xsph.ru/images/LE_SSERAFIM_-_EASY_77468865.mp3",
//    "https://static.wikia.nocookie.net/kpop/images/2/23/LE_SSERAFIM_Easy_digital_cover.png/revision/latest?cb=20240219121927&path-prefix=ru"
//    ),
//    Song(
//    "Stray Kids",
//    "LALALA",
//    "http://f0862137.xsph.ru/images/Stray_Kids_-_LALALA_76943246.mp3",
//    "https://static.wikia.nocookie.net/stray-kids/images/5/5f/LALALALA_MV_gif.gif/revision/latest?cb=20231112234310"
//    ),
//    Song(
//    "Ariana Grande",
//    "7 rings",
//    "http://f0862137.xsph.ru/images/Ariana_Grande_-_7_rings_61522389.mp3",
//    "https://static.wikia.nocookie.net/arianagrande/images/e/ef/7_Rings_Cover.jpg/revision/latest/scale-to-width-down/1000?cb=20190206061540"
//    ),
//    Song(
//    "Avril Lavigne",
//    "Bite Me",
//    "http://f0862137.xsph.ru/images/Avril_Lavigne_-_Bite_Me_73300491.mp3",
//    "https://static.wikia.nocookie.net/avrillavigne/images/2/2c/Bite_Me.jpg/revision/latest?cb=20211111215256"
//    ),
//    Song(
//    "Dreamcatcher",
//    "Deja Vu",
//    "http://f0862137.xsph.ru/images/Dreamcatcher_-_Deja_Vu_68900495.mp3",
//    "https://static.wikia.nocookie.net/dreamcatcherwiki/images/b/b3/Raid_of_Dream_Digital_Album_Cover.png/revision/latest/scale-to-width-down/1000?cb=20191127014333"
//    ),
//    Song(
//    "Hollywood Undead",
//    "We Are",
//    "http://f0862137.xsph.ru/images/Hollywood_Undead_-_We_Are_47894453.mp3",
//    "https://static.wikia.nocookie.net/hollywoodundead/images/5/5d/Swan_Songs1.png/revision/latest/scale-to-width-down/1000?cb=20150313212021"
//    ),
//    Song(
//    "Jonas Brothers",
//    "Burnin Up",
//    "http://f0862137.xsph.ru/images/Jonas_Brothers_-_Burnin_Up_48385614.mp3",
//    "https://i1.sndcdn.com/artworks-000120469024-6g0tto-t500x500.jpg"
//    ),
//    Song(
//    "LE SSERAFIM",
//    "FEARLESS",
//    "http://f0862137.xsph.ru/images/LE_SSERAFIM_-_FEARLESS_74181268.mp3",
//    "https://static.wikia.nocookie.net/le-sserafim/images/4/45/FEARLESS_digital_album_cover.jpg/revision/latest/scale-to-width-down/1000?cb=20220502104016"
//    ),
//    Song(
//    "LISA",
//    "MONEY",
//    "http://f0862137.xsph.ru/images/LISA_-_MONEY_73159159.mp3",
//    "https://static.wikia.nocookie.net/blinks/images/2/20/Lisa_Lalisa_digital_album_cover.jpeg/revision/latest?cb=20210910040123"
//    ),
//    Song(
//    "Linkin Park",
//    "Don`t Stay",
//    "http://f0862137.xsph.ru/images/Linkin_Park_-_Dont_Stay_47828660.mp3",
//    "https://sun9-1.userapi.com/impf/c638718/v638718073/3562f/Bm6pEEYsx1c.jpg?size=1024x1024&quality=96&sign=aea537bdf6cd5cedc4fea3e207755687&c_uniq_tag=R36FXHm1P6-ZBGUcXgNBtTEB8hmlBhjXmqZSVGGFj6w&type=album"
//    ),
//    Song(
//    "aespa",
//    "Drama",
//    "http://f0862137.xsph.ru/images/aespa_-_Drama_76980884.mp3",
//    "https://static.wikia.nocookie.net/kpop/images/e/ee/Aespa_Drama_digital_album_cover.png/revision/latest?cb=20231109060919"
//    )
//    ).shuffled()
}