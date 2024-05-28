package org.easyprog.musicapp.ui.screens.artist_songs.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
import data.model.Song

@Composable
fun ArtistSongsListComponent(modifier: Modifier = Modifier, songsList: List<Song>) {
    LazyColumn(
        modifier = modifier
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
        modifier = modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(modifier = Modifier.size(80.dp), model = songArtwork, contentDescription = null)
        DefaultText(text = songTitle, fontSize = 20.sp)
    }
}