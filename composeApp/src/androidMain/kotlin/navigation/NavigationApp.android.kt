package navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.Dispatchers
import org.easyprog.musicapp.ui.screens.artist_songs.ArtistSongsScreen
import org.easyprog.musicapp.ui.screens.artists_list.ArtistsListScreen
import org.easyprog.musicapp.ui.screens.home.HomeScreen
import org.easyprog.musicapp.ui.screens.player.PlayerScreen
import org.koin.androidx.compose.koinViewModel
import ui.SharedViewModel
import ui.artist_songs.ArtistSongsViewModel
import ui.artists_list.ArtistsListViewModel
import ui.home.HomeViewModel
import ui.player.PlayerViewModel

@Composable
actual fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel) {
    val audioPlayerUiState = sharedViewModel.audioPlayerUiState

    NavHost(navController = navController,
        startDestination = Destinations.homeScreen,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(route = Destinations.homeScreen) {
            val homeViewModel: HomeViewModel = koinViewModel()

            HomeScreen(
                audioPlayerUiState = audioPlayerUiState,
                uiState = homeViewModel.homeScreenUiState,
                onEvent = homeViewModel::onEvent,
                onPlayerScreen = { navController.navigate(Destinations.playerSongListScreen) },
                onArtistsListScreen = { navController.navigate(Destinations.artistsListScreen) },
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
            arguments = listOf(navArgument("idArtist") { type = NavType.LongType }),
            enterTransition = {
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(300, easing = LinearEasing)
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }
        ) {
            val artistSongsViewModel: ArtistSongsViewModel = koinViewModel()

            ArtistSongsScreen(
                uiState = artistSongsViewModel.artistSongsUiState,
                audioPlayerUiState = sharedViewModel.audioPlayerUiState,
                onEvent = artistSongsViewModel::onEvent,
                setSongsList = sharedViewModel::setSongsList,
                onPlayerScreen = { navController.navigate(Destinations.playerSongListScreen) },
                onBack = navController::navigateUp
            )
        }

        composable(route = Destinations.artistsListScreen) {
            val artistsListViewModel: ArtistsListViewModel = koinViewModel()

            ArtistsListScreen(
                uiState = artistsListViewModel.artistsListScreenUiState,
                onArtistSongsList = { idArtist, nameArtist ->  navController.navigate("${Destinations.artistSongsScreen}/$idArtist/$nameArtist")},
                onBack = navController::navigateUp
            )
        }
    }
}