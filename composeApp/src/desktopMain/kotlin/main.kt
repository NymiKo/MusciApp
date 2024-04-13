import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import screens.MainScreen


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "MusicApp",
        icon = null,
        state = WindowState(width = 1145.dp, height = 775.dp)
    ) {
        MaterialTheme {
            MainScreen()
        }
    }
}