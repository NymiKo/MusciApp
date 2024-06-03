package org.easyprog.musicapp.ui.screens.artists_list.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
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
                artistImage = artistsList[index].urlImage,
                artistName = artistsList[index].name
            )
        }
    }
}

@Composable
fun ArtistItem(modifier: Modifier = Modifier, artistImage: String, artistName: String) {
    Card(
        modifier = modifier.aspectRatio(1F),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ArtistImage(artistImage = artistImage)
            ArtistName(modifier = Modifier.align(Alignment.BottomCenter), artistName = artistName)
        }
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

@Composable
fun ArtistName(modifier: Modifier = Modifier, artistName: String) {
    DefaultText(
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary).padding(4.dp),
        text = artistName,
        fontSize = 18.sp,
        letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
        color = MaterialTheme.colorScheme.secondary,
        maxLines = 2
    )
}