package org.easyprog.musicapp.ui.screens.artists_list.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import data.model.Artist

@Composable
fun ArtistsList(
    modifier: Modifier = Modifier,
    artistsList: List<Artist>,
    onArtistSongsList: (idArtist: Long, nameArtist: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(count = artistsList.size, key = { artistsList[it].id }) { index ->
            ArtistItem(
                modifier = Modifier.clickable {
                    onArtistSongsList(artistsList[index].id, artistsList[index].name)
                },
                artistImage = artistsList[index].urlImage
            )
        }
    }
}

@Composable
fun ArtistItem(modifier: Modifier = Modifier, artistImage: String) {
    Card(
        modifier = modifier.aspectRatio(1F)
    ) {
        ArtistImage(artistImage = artistImage)
    }
}

@Composable
fun ArtistImage(modifier: Modifier = Modifier, artistImage: String) {
    AsyncImage(
        modifier = modifier.fillMaxSize(),
        model = artistImage,
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}