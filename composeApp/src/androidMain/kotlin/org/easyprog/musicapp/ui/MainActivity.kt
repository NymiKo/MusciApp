package org.easyprog.musicapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import audio_player.MediaService
import navigation.AppNavHost
import themes.AppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import ui.SharedViewModel

class MainActivity : ComponentActivity() {
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Surface {
                    AppNavHost(navController = navController, sharedViewModel = sharedViewModel)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sharedViewModel.releaseMediaPlayer()
        stopService(Intent(this, MediaService::class.java))
    }
}