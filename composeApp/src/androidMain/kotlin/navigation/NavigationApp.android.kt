package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.easyprog.musicapp.ui.screens.artist_songs.ArtistSongsScreen
import org.easyprog.musicapp.ui.screens.home.HomeScreen
import org.easyprog.musicapp.ui.screens.player.PlayerScreen
import org.koin.androidx.compose.koinViewModel
import ui.SharedViewModel
import ui.artist_songs.ArtistSongsViewModel
import ui.home.HomeEvents
import ui.home.HomeViewModel
import ui.player.PlayerViewModel

@Composable
actual fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val audioPlayerUiState = sharedViewModel.audioPlayerUiState

    NavHost(navController = navController, startDestination = Destinations.homeScreen) {
        composable(route = Destinations.homeScreen) {
            val homeViewModel: HomeViewModel = koinViewModel()
            var isInitialized by rememberSaveable { mutableStateOf(false) }

            if (!isInitialized) {
                LaunchedEffect(Unit) {
                    homeViewModel.onEvent(HomeEvents.FetchData)
                    isInitialized = true
                }
            }

            HomeScreen(
                audioPlayerUiState = audioPlayerUiState,
                uiState = homeViewModel.homeScreenUiState,
                onEvent = homeViewModel::onEvent,
                onPlayerScreen = { navController.navigate(Destinations.playerSongListScreen) },
                setSongsList = { sharedViewModel.setSongsList(it) },
                getSongsListMyWave = sharedViewModel::getSongs,
                onArtistSongsList = { idArtist, nameArtist -> navController.navigate("${Destinations.artistSongsScreen}/$idArtist/$nameArtist") }
            )
        }

        composable(route = Destinations.playerSongListScreen) {
            val playerViewModel: PlayerViewModel = koinViewModel()

            PlayerScreen(
                audioPlayerUiState = audioPlayerUiState,
                uiState = playerViewModel.playerUiState,
                onEvent = playerViewModel::onEvent,
                onBack = navController::navigateUp
            )
        }

        composable(
            route = "${Destinations.artistSongsScreen}/{idArtist}/{nameArtist}",
            arguments = listOf(
                navArgument("idArtist") { type = NavType.LongType })
        ) {
            val artistSongsViewModel: ArtistSongsViewModel = koinViewModel()

            ArtistSongsScreen(
                uiState = artistSongsViewModel.artistSongsUiState,
                onBack = navController::navigateUp
            )
        }
    }
}