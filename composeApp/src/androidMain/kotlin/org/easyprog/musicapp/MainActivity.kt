package org.easyprog.musicapp

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.easyprog.musicapp.screens.TrendsScreen
import org.easyprog.musicapp.screens.player_view.PlayerComponent
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            Column(
//                modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(12.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                TrendsScreen(modifier = Modifier.weight(1F))
//            }
            PlayerComponent(modifier = Modifier, viewModel = getKoin().get())
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}