import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import audio_player.AudioPlayerUiState
import di.ktorModule
import di.mediaControllerModule
import di.repositoryModule
import di.useCaseModule
import di.viewModelModule
import navigation.Destinations
import org.koin.core.Koin
import org.koin.core.context.startKoin
import screens.home.HomeScreen
import ui.SharedViewModel
import ui.home.HomeViewModel

lateinit var koin: Koin

fun main() = application {

    koin = startKoin {
        modules(mediaControllerModule, ktorModule, repositoryModule, useCaseModule, viewModelModule)
    }.koin

    val sharedViewModel: SharedViewModel = koin.get()

    Window(
        onCloseRequest = ::exitApplication,
        title = "MusicApp",
        icon = null,
        state = WindowState(width = 1145.dp, height = 775.dp)
    ) {
        MaterialTheme {
            Surface {
                val homeViewModel: HomeViewModel = koin.get()

                HomeScreen(
                    audioPlayerUiState = sharedViewModel.audioPlayerUiState,
                    uiState = homeViewModel.homeScreenUiState,
                    onEvent = homeViewModel::onEvent,
                    onPlayerScreen = {  },
                    onArtistsListScreen = {  },
                    setSongsList = { sharedViewModel.setSongsList(it) },
                    getSongsListMyWave = sharedViewModel::getSongs,
                    onArtistSongsList = { idArtist, nameArtist ->  }
                )
            }
        }
    }
}