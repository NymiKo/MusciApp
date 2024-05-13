import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
import di.mediaControllerModule
import org.koin.core.Koin
import org.koin.core.context.startKoin
import screens.TrendsScreen
import screens.player_view.PlayerComponent

lateinit var koin: Koin

fun main() = application {

    koin = startKoin {
        modules(mediaControllerModule)
    }.koin

    Window(
        onCloseRequest = ::exitApplication,
        title = "MusicApp",
        icon = null,
        state = WindowState(width = 1145.dp, height = 775.dp)
    ) {
        MaterialTheme {
            Row(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.95F))
            ) {
                Title(modifier = Modifier)
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TrendsScreen(modifier = Modifier.weight(1F))
                    PlayerComponent(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun Title(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(horizontal = 40.dp, vertical = 8.dp)
    ) {
        Text(
            modifier = Modifier,
            fontWeight = FontWeight.Bold,
            text = "Не яндекс",
            fontSize = 16.sp,
            color = Color.Yellow
        )
        Text(
            modifier = Modifier,
            text = "Музыка",
            fontSize = 26.sp,
            letterSpacing = TextUnit(4F, TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = Color.Yellow
        )
    }
}