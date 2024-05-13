package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.java.KoinJavaComponent.getKoin
import ui.SharedViewModel
import ui.player.MediaViewModel

@Composable
expect fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel)