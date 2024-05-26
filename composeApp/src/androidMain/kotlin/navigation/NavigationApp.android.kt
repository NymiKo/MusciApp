package navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
actual fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val audioPlayerUiState = sharedViewModel.audioPlayerUiState

    SharedTransitionLayout {
        NavHost(navController = navController, startDestination = Destinations.home) {
            composable(route = Destinations.home) {
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
                    animatedContentScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    uiState = homeViewModel.homeScreenUiState,
                    onEvent = homeViewModel::onEvent,
                    onPlayerScreen = { navController.navigate(Destinations.playerSongListScreen) },
                    setSongsList = { sharedViewModel.setSongsList(it) },
                    getSongsListMyWave = sharedViewModel::getSongs
                )
            }

            composable(route = Destinations.playerSongListScreen) {
                val playerViewModel: PlayerViewModel = koinViewModel()

                PlayerScreen(
                    animatedContentScope = this@composable,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    audioPlayerUiState = audioPlayerUiState,
                    uiState = playerViewModel.playerUiState,
                    onEvent = playerViewModel::onEvent,
                    getSongsListMyWave = sharedViewModel::getSongs,
                    onBack = navController::navigateUp
                )
            }
        }
    }
}