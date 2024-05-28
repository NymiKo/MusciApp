package org.easyprog.musicapp.ui.screens.player

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import data.model.Song
import org.easyprog.musicapp.ui.screens.player.uicomponents.DetailsMediaComponent
import org.easyprog.musicapp.ui.screens.player.uicomponents.PlayerControlRow
import org.easyprog.musicapp.ui.screens.player.uicomponents.SongsListColumn
import org.easyprog.musicapp.ui.screens.player.uicomponents.TopAppBarPlayer
import ui.player.PlayerEvents
import ui.player.PlayerScreenUiState

@Composable
fun PlayerScreen(
    modifier: Modifier = Modifier,
    audioPlayerUiState: AudioPlayerUiState,
    uiState: PlayerScreenUiState,
    onEvent: (PlayerEvents) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBarPlayer(onBack = onBack::invoke)
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
                if (audioPlayerUiState.playerState == AudioPlayerState.PLAYING) onEvent(
                    PlayerEvents.PauseSong
                )
                else onEvent(PlayerEvents.ResumeSong)
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