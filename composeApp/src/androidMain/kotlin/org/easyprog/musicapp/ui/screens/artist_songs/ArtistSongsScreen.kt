package org.easyprog.musicapp.ui.screens.artist_songs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.easyprog.musicapp.ui.screens.artist_songs.uicomponents.ArtistSongsListComponent
import org.easyprog.musicapp.ui.screens.artist_songs.uicomponents.TopAppBarCustom
import ui.artist_songs.ArtistSongsScreenUiState

@Composable
fun ArtistSongsScreen(modifier: Modifier = Modifier, uiState: ArtistSongsScreenUiState, onBack: () -> Unit) {
    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBarCustom(title = uiState.nameArtist, onBack = onBack::invoke)
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(modifier = Modifier.size(300.dp), model = uiState.imageArtist, contentDescription = null)
            }
            ArtistSongsListComponent(songsList = emptyList())
        }
    }
}