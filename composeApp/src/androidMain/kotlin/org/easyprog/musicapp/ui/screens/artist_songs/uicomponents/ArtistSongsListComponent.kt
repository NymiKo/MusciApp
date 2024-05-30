package org.easyprog.musicapp.ui.screens.artist_songs.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
import data.model.Song
import org.easyprog.musicapp.ui.theme.Purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistSongsListComponent(modifier: Modifier = Modifier, songsList: List<Song>, scrollBehavior: TopAppBarScrollBehavior) {
    Column(
        modifier = modifier.padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayButton()
        ArtistSongsLazyColumn(songsList = songsList)
    }
}

@Composable
fun PlayButton(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = modifier.size(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Purple),
            shape = CircleShape,
            onClick = {

            }
        ) {
            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.Black)
        }
        DefaultText(
            text = "Слушать",
            fontSize = 16.sp
        )
    }
}

@Composable
fun ArtistSongsLazyColumn(modifier: Modifier = Modifier, songsList: List<Song>) {
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
                songArtwork = songsList[it].urlImage,
                songTitle = songsList[it].title
            )
        }
    }
}

@Composable
private fun ArtistSongItem(modifier: Modifier = Modifier, songArtwork: String, songTitle: String) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(modifier = Modifier.size(70.dp).clip(RoundedCornerShape(8.dp)), model = songArtwork, contentDescription = null)
        DefaultText(text = songTitle, fontSize = 20.sp)
    }
}