package org.easyprog.musicapp.ui.screens.player.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import themes.Purple

@Composable
fun PlayerControlRow(
    modifier: Modifier = Modifier,
    currentPlayingSongIndex: Int,
    lastSongIndex: Int,
    isPlaying: Boolean,
    isShuffleModeEnabled: Boolean,
    isRepeatModeEnabled: Boolean,
    prevSong: () -> Unit,
    pauseOrPlay: () -> Unit,
    nextSong: () -> Unit,
    changeRepeatMode: () -> Unit,
    changeShuffleMode: () -> Unit
) {
    Row(
        modifier = modifier.padding(8.dp).fillMaxWidth().shadow(4.dp, CircleShape)
            .background(MaterialTheme.colorScheme.primary, CircleShape).padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
    ) {
        PlayerIcon(
            size = 25.dp,
            imageVector = Icons.Default.Loop,
            tint = if (isRepeatModeEnabled) Purple else MaterialTheme.colorScheme.secondary,
            onClick = changeRepeatMode::invoke
        )

        PlayerIcon(
            size = 35.dp,
            imageVector = Icons.Default.SkipPrevious,
            tint = if (currentPlayingSongIndex == 0 && !isRepeatModeEnabled) Color.Gray else MaterialTheme.colorScheme.secondary,
            onClick = prevSong::invoke
        )

        PlayerIcon(
            size = 65.dp,
            imageVector = if (isPlaying) Icons.Filled.PauseCircle else Icons.Filled.PlayCircle,
            tint = Purple,
            onClick = pauseOrPlay::invoke
        )

        PlayerIcon(
            size = 35.dp,
            imageVector = Icons.Filled.SkipNext,
            tint = if (currentPlayingSongIndex == lastSongIndex && !isRepeatModeEnabled) Color.Gray else MaterialTheme.colorScheme.secondary,
            onClick = nextSong::invoke
        )

        PlayerIcon(
            size = 25.dp,
            imageVector = Icons.Default.Shuffle,
            tint = if (isShuffleModeEnabled) Purple else MaterialTheme.colorScheme.secondary,
            onClick = changeShuffleMode::invoke
        )
    }
}

@Composable
private fun PlayerIcon(
    size: Dp,
    imageVector: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Icon(
        modifier = Modifier.size(size).clickable(interactionSource = remember { MutableInteractionSource() }, indication = null) { onClick() },
        imageVector = imageVector,
        contentDescription = null,
        tint = tint
    )
}