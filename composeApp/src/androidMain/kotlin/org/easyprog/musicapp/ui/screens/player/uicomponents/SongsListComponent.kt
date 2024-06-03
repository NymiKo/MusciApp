package org.easyprog.musicapp.ui.screens.player.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import custom_elements.text.DefaultText
import data.model.Song

@Composable
fun SongsListColumn(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    currentPlayingSongIndex: Int,
    scrollToSong: (page: Int) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(count = songsList.size, key = { songsList[it].id }) { index ->
            SongItem(
                modifier = Modifier.clickable {
                    scrollToSong(index)
                },
                song = songsList[index],
                isPlaying = currentPlayingSongIndex == index
            )
        }
    }
}

@Composable
fun SongItem(
    modifier: Modifier = Modifier,
    song: Song,
    isPlaying: Boolean
) {
    Row(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SongArtistAndTitle(modifier = Modifier.weight(1F), artist = song.artist, title = song.title)

        NowPlayingSong(modifier = Modifier.align(Alignment.Bottom), isPlaying = isPlaying)
    }
}

@Composable
fun SongArtistAndTitle(modifier: Modifier = Modifier, artist: String, title: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DefaultText(
            text = title,
            color = MaterialTheme.colorScheme.secondary,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
            fontSize = 20.sp,
            textAlign = TextAlign.Start
        )

        DefaultText(
            text = artist,
            color = Color.Gray,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
            fontSize = 16.sp,
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun NowPlayingSong(
    modifier: Modifier = Modifier,
    isPlaying: Boolean
) {
    if (isPlaying) {
        DefaultText(
            modifier = modifier,
            text = "Сейчас играет",
            color = Color.White,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
            fontSize = 16.sp
        )
    }
}