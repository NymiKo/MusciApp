package navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ui.SharedViewModel

@Composable
expect fun AppNavHost(navController: NavHostController, sharedViewModel: SharedViewModel)