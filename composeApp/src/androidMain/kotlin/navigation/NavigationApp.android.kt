package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.easyprog.musicapp.ui.screens.player_view.PlayerScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.java.KoinJavaComponent
import org.koin.java.KoinJavaComponent.getKoin
import ui.SharedViewModel
import ui.player.MediaViewModel

@Composable
actual fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val audioPlayerUiState = sharedViewModel.audioPlayerUiState

    NavHost(navController = navController, startDestination = Destinations.playerSongListScreen) {
        composable(route = Destinations.playerSongListScreen) {
            val mediaViewModel: MediaViewModel = koinViewModel()

            PlayerScreen(
                audioPlayerUiState = audioPlayerUiState,
                viewModel = mediaViewModel
            )
        }
    }
}