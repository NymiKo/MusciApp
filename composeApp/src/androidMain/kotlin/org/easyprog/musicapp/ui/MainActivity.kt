package org.easyprog.musicapp.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import audio_player.MediaService
import org.easyprog.musicapp.ui.screens.player_view.PlayerComponent
import org.easyprog.musicapp.ui.theme.AppTheme
import org.easyprog.musicapp.ui.theme.PurpleLight
import org.koin.android.ext.android.getKoin

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            navigationBarStyle = SystemBarStyle.light(
                Color.TRANSPARENT,
                Color.TRANSPARENT
            )
        )
        setContent {
            AppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {},
                            navigationIcon = {
                                Icon(
                                    modifier = Modifier.padding(8.dp).size(35.dp),
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors()
                                .copy(containerColor = PurpleLight)
                        )
                    }
                ) { paddingValues ->
                    PlayerComponent(
                        modifier = Modifier.padding(paddingValues),
                        viewModel = getKoin().get()
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        stopService(Intent(this, MediaService::class.java))
    }
}