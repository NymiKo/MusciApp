package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.easyprog.musicapp.ui.screens.home.HomeScreen
import org.easyprog.musicapp.ui.screens.player.PlayerScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import ui.SharedViewModel
import ui.home.HomeViewModel
import ui.player.MediaViewModel

@Composable
actual fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val audioPlayerUiState = sharedViewModel.audioPlayerUiState

    NavHost(navController = navController, startDestination = Destinations.home) {
        composable(route = Destinations.home) {
            val homeViewModel: HomeViewModel = koinViewModel()

            HomeScreen(viewModel = homeViewModel)
        }

        composable(route = Destinations.playerSongListScreen) {
            val mediaViewModel: MediaViewModel = koinViewModel()

            PlayerScreen(
                audioPlayerUiState = audioPlayerUiState,
                viewModel = mediaViewModel
            )
        }
    }
}