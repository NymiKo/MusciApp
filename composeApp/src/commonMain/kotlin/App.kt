import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.Song
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme(colorScheme = darkColorScheme()) {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        val listMusic = listOf(
            "", "", "", ""
        )
        Surface(color = Color.Transparent, modifier = Modifier.background(Color.Transparent)) {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxWidth(0.4F).background(Color.Transparent)
                    .padding(vertical = 8.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(listMusic) { song ->
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                            .clip(RoundedCornerShape(10.dp)).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(80.dp),
                            imageVector = Icons.Default.Done,
                            contentDescription = null
                        )
                        Text(modifier = Modifier.padding(start = 8.dp), text = song)
                    }
                }
            }
        }
    }
}