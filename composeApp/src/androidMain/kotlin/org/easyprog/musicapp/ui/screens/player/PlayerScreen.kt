package org.easyprog.musicapp.ui.screens.player

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Loop
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import coil3.compose.AsyncImage
import custom_elements.slider.customSliderColors
import custom_elements.text.DefaultText
import custom_elements.text.TimeText
import ui.player.PlayerViewModel
import data.model.Song
import org.easyprog.musicapp.ui.screens.player.uicomponents.DetailsMediaComponent
import org.easyprog.musicapp.ui.screens.player.uicomponents.PlayerControlRow
import org.easyprog.musicapp.ui.screens.player.uicomponents.SongsListColumn
import org.easyprog.musicapp.ui.theme.Purple
import org.easyprog.musicapp.ui.theme.PurpleDark
import org.easyprog.musicapp.ui.theme.PurpleLight
import ui.player.PlayerEvents
import ui.player.PlayerScreenUiState
import utils.toTimeString
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    audioPlayerUiState: AudioPlayerUiState,
    uiState: PlayerScreenUiState,
    onEvent: (PlayerEvents) -> Unit,
    getSongsListMyWave: () -> Unit,
    onBack: () -> Unit
) {
    if (audioPlayerUiState.currentPosition == audioPlayerUiState.currentSongsList.lastIndex && !audioPlayerUiState.endGetSongs) {
        getSongsListMyWave()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.clickable { onBack() },
                title = {},
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(8.dp).size(35.dp),
                        imageVector = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = PurpleLight)
            )
        }
    ) { paddingValues ->
        PlayerScreen(
            modifier = modifier.padding(paddingValues),
            songsList = audioPlayerUiState.currentSongsList,
            currentPlayingSongIndex = audioPlayerUiState.currentPosition,
            currentTime = audioPlayerUiState.currentTime.toFloat(),
            fullTime = audioPlayerUiState.totalTime,
            isPlaying = audioPlayerUiState.playerState == AudioPlayerState.PLAYING,
            isRepeatModeEnabled = audioPlayerUiState.isRepeat,
            isShuffleModeEnabled = audioPlayerUiState.isShuffle,
            changeTime = { onEvent(PlayerEvents.ChangeTime(it)) },
            prevSong = { onEvent(PlayerEvents.PrevSong) },
            pauseOrPlay = {
                if (audioPlayerUiState.playerState == AudioPlayerState.PLAYING) onEvent(PlayerEvents.PauseSong) else onEvent(PlayerEvents.ResumeSong)
            },
            nextSong = { onEvent(PlayerEvents.NextSong) },
            scrollToSong = { onEvent(PlayerEvents.ScrollToSong(it)) },
            changeRepeatMode = { onEvent(PlayerEvents.ChangeRepeatMode) },
            changeShuffleMode = { onEvent(PlayerEvents.ChangeShuffleMode) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PlayerScreen(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    currentPlayingSongIndex: Int,
    currentTime: Float,
    fullTime: Long,
    isPlaying: Boolean,
    isRepeatModeEnabled: Boolean,
    isShuffleModeEnabled: Boolean,
    changeTime: (time: Float) -> Unit,
    prevSong: () -> Unit,
    pauseOrPlay: () -> Unit,
    nextSong: () -> Unit,
    scrollToSong: (page: Int) -> Unit,
    changeRepeatMode: () -> Unit,
    changeShuffleMode: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(pageCount = { songsList.size })

        DetailsMediaComponent(
            pagerState = pagerState,
            songsList = songsList,
            currentTime = currentTime,
            currentPlayingSongIndex = currentPlayingSongIndex,
            fullTime = fullTime,
            changeTime = changeTime::invoke
        )

        SongsListColumn(
            modifier = Modifier.padding(top = 16.dp).weight(1F),
            songsList = songsList,
            currentPlayingSongIndex = currentPlayingSongIndex,
            scrollToSong = scrollToSong::invoke
        )

        PlayerControlRow(
            currentPlayingSongIndex = currentPlayingSongIndex,
            lastSongIndex = songsList.lastIndex,
            isPlaying = isPlaying,
            isRepeatModeEnabled = isRepeatModeEnabled,
            isShuffleModeEnabled = isShuffleModeEnabled,
            nextSong = nextSong::invoke,
            pauseOrPlay = pauseOrPlay::invoke,
            prevSong = prevSong::invoke,
            changeRepeatMode = changeRepeatMode::invoke,
            changeShuffleMode = changeShuffleMode::invoke
        )
    }
}