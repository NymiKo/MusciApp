package org.easyprog.musicapp.ui.screens.home.uicomponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
import data.model.SongMetadata
import themes.PurpleDark

@Composable
fun BottomPlayerComponent(
    modifier: Modifier = Modifier,
    audioPlayerUiState: AudioPlayerUiState,
    resumeSong: () -> Unit,
    pauseSong: () -> Unit,
    onPlayerScreen: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = audioPlayerUiState.playerState != AudioPlayerState.STOPPED,
        enter = slideInVertically { it },
    ) {
        if (audioPlayerUiState.currentSong != null) {
            BottomPlayer(
                song = audioPlayerUiState.currentSong,
                playerState = audioPlayerUiState.playerState,
                resumeSong = resumeSong,
                pauseSong = pauseSong,
                onPlayerScreen = onPlayerScreen::invoke
            )
        }
    }
}

@Composable
private fun BottomPlayer(
    modifier: Modifier = Modifier,
    song: SongMetadata,
    playerState: AudioPlayerState,
    resumeSong: () -> Unit,
    pauseSong: () -> Unit,
    onPlayerScreen: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().height(70.dp)
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)).background(PurpleDark)
            .clickable { onPlayerScreen() }.padding(start = 8.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(55.dp).clip(RoundedCornerShape(16.dp)),
            model = song.artwork,
            contentDescription = null
        )
        Column(
            modifier = Modifier.padding(start = 16.dp).weight(1F),
            verticalArrangement = Arrangement.Center
        ) {
            DefaultText(
                text = song.title,
                color = MaterialTheme.colorScheme.secondary,
                letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                fontSize = 18.sp
            )

            DefaultText(
                text = song.artist,
                color = Color.Gray,
                letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
                fontSize = 16.sp
            )
        }
        Icon(
            modifier = Modifier.size(50.dp).clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (playerState == AudioPlayerState.PLAYING) {
                    pauseSong()
                } else {
                    resumeSong()
                }
            },
            imageVector = if (playerState == AudioPlayerState.PLAYING) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White
        )
    }
}