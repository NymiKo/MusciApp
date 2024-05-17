package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.easyprog.musicapp.ui.screens.home.HomeScreen
import org.easyprog.musicapp.ui.screens.player.PlayerScreen
import org.koin.androidx.compose.koinViewModel
import ui.SharedViewModel
import ui.home.HomeEvents
import ui.home.HomeViewModel
import ui.player.PlayerViewModel

@Composable
actual fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val audioPlayerUiState = sharedViewModel.audioPlayerUiState

    NavHost(navController = navController, startDestination = Destinations.home) {
        composable(route = Destinations.home) {
            val homeViewModel: HomeViewModel = koinViewModel()
            homeViewModel.onEvent(HomeEvents.FetchData)

            HomeScreen(
                audioPlayerUiState = audioPlayerUiState,
                uiState = homeViewModel.homeScreenUiState,
                onEvent = homeViewModel::onEvent,
                onPlayerScreen = { navController.navigate(Destinations.playerSongListScreen) },
            )
        }

        composable(route = Destinations.playerSongListScreen) {
            val playerViewModel: PlayerViewModel = koinViewModel()

            PlayerScreen(
                audioPlayerUiState = audioPlayerUiState,
                viewModel = playerViewModel,
                onBack = navController::navigateUp
            )
        }
    }
}