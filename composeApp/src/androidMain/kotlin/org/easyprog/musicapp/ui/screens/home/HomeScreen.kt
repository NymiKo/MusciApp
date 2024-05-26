package org.easyprog.musicapp.ui.screens.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import custom_elements.text.DefaultText
import data.model.Song
import org.easyprog.musicapp.ui.screens.home.uicomponents.ArtistsComponent
import org.easyprog.musicapp.ui.screens.home.uicomponents.BottomPlayerComponent
import org.easyprog.musicapp.ui.screens.home.uicomponents.LastSongsComponent
import org.easyprog.musicapp.ui.screens.home.uicomponents.MyWaveComponent
import ui.home.HomeEvents
import ui.home.HomeScreenUiState

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    animatedContentScope: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    audioPlayerUiState: AudioPlayerUiState,
    uiState: HomeScreenUiState,
    onEvent: (HomeEvents) -> Unit,
    onPlayerScreen: () -> Unit,
    setSongsList: (songsList: List<Song>) -> Unit,
    getSongsListMyWave: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
            .statusBarsPadding().navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.padding(top = 48.dp).fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            MyWaveComponent(getSongsListMyWave = getSongsListMyWave::invoke)
            LastSongsComponent(
                lastSongsList = uiState.lastSongsList,
                playSong = { _, indexSong ->
                    setSongsList(uiState.lastSongsList)
                    onEvent(HomeEvents.PlaySong(indexSong))
                }
            )
            ArtistsComponent(artistsList = uiState.artistsList)
            if (audioPlayerUiState.playerState != AudioPlayerState.STOPPED) {
                Spacer(modifier = Modifier.fillMaxWidth().height(80.dp))
            }
        }
        BottomPlayerComponent(
            modifier = Modifier.navigationBarsPadding().align(Alignment.BottomCenter),
            animatedContentScope = animatedContentScope,
            sharedTransitionScope = sharedTransitionScope,
            audioPlayerUiState = audioPlayerUiState,
            onEvent = onEvent::invoke,
            onPlayerScreen = onPlayerScreen::invoke
        )
    }
}

@Composable
fun NameCategory(modifier: Modifier = Modifier, text: String) {
    DefaultText(
        modifier = modifier.padding(horizontal = 16.dp).fillMaxWidth(),
        text = text,
        fontSize = 26.sp,
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Start
    )
}


