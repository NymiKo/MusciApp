package org.easyprog.musicapp.ui.screens.artist_songs.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import audio_player.AudioPlayerState
import audio_player.AudioPlayerUiState
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
import data.model.Song
import org.easyprog.musicapp.ui.screens.player.uicomponents.NowPlayingSong
import org.easyprog.musicapp.ui.screens.player.uicomponents.SongArtistAndTitle
import themes.Purple
import ui.artist_songs.ArtistSongsEvents

@Composable
fun ArtistSongsListComponent(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    audioPlayerUiState: AudioPlayerUiState,
    onEvent: (ArtistSongsEvents) -> Unit,
    setSongsList: (songsList: List<Song>) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayButton(
            songsList = songsList,
            audioPlayerUiState = audioPlayerUiState,
            onEvent = onEvent::invoke,
            setSongsList = setSongsList::invoke
        )
        ArtistSongsLazyColumn(
            songsList = songsList,
            audioPlayerUiState = audioPlayerUiState,
            onEvent = onEvent::invoke,
            setSongsList = setSongsList::invoke
        )
    }
}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    audioPlayerUiState: AudioPlayerUiState,
    onEvent: (ArtistSongsEvents) -> Unit,
    setSongsList: (songsList: List<Song>) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.size(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple),
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp),
            onClick = {
                togglePlayButton(
                    indexSong = 0,
                    audioPlayerUiState = audioPlayerUiState,
                    songsList = songsList,
                    setSongsList = setSongsList::invoke,
                    onEvent = onEvent::invoke
                )
            }
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = if (audioPlayerUiState.currentSongsList == songsList && audioPlayerUiState.playerState == AudioPlayerState.PLAYING) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.Black
            )
        }
        DefaultText(
            text = "Слушать",
            fontSize = 16.sp
        )
    }
}

@Composable
fun ArtistSongsLazyColumn(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    audioPlayerUiState: AudioPlayerUiState,
    onEvent: (ArtistSongsEvents) -> Unit,
    setSongsList: (songsList: List<Song>) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(top = 16.dp).background(
            MaterialTheme.colorScheme.background,
            RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ).clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)).fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(songsList.size) {
            ArtistSongItem(
                modifier = Modifier.clickable {
                    if (audioPlayerUiState.currentSongsList != songsList) {
                        setSongsList(songsList)
                    }
                    onEvent(ArtistSongsEvents.PlaySong(it))
                },
                song = songsList[it],
                isPlaying = audioPlayerUiState.currentSong?.mediaId == songsList[it].urlMusic
            )
        }
    }
}

@Composable
private fun ArtistSongItem(
    modifier: Modifier = Modifier,
    isPlaying: Boolean,
    song: Song
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)),
            model = song.urlImage,
            contentDescription = null
        )
        SongArtistAndTitle(modifier = Modifier.weight(1F), artist = song.artist, title = song.title)
        NowPlayingSong(modifier = Modifier.align(Alignment.Bottom), isPlaying = isPlaying)
    }
}

fun togglePlayButton(
    indexSong: Int,
    audioPlayerUiState: AudioPlayerUiState,
    songsList: List<Song>,
    setSongsList: (songsList: List<Song>) -> Unit,
    onEvent: (ArtistSongsEvents) -> Unit
) {
    if (audioPlayerUiState.currentSongsList == songsList) {
        when (audioPlayerUiState.playerState) {
            AudioPlayerState.PLAYING -> onEvent(ArtistSongsEvents.PauseSong)
            else -> onEvent(ArtistSongsEvents.ResumeSong)
        }
    } else {
        setSongsList(songsList)
        onEvent(ArtistSongsEvents.PlaySong(indexSong))
    }
}