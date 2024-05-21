package org.easyprog.musicapp.ui.screens.home.uicomponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import custom_elements.text.DefaultText
import data.model.Artist
import org.easyprog.musicapp.ui.screens.home.NameCategory

@Composable
fun ArtistsComponent(modifier: Modifier = Modifier, artistsList: List<Artist>) {
    Column(
        modifier = modifier.padding(top = 48.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        NameCategory(text = "Исполнители")
        ArtistsRow(artistsList = artistsList)
    }
}

@Composable
private fun ArtistsRow(modifier: Modifier = Modifier, artistsList: List<Artist>) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(count = artistsList.size, key = { artistsList[it].id }) {
            ArtistItem(
                artistName = artistsList[it].name,
                artistImage = artistsList[it].urlImage
            )
        }
    }
}

@Composable
fun ArtistItem(modifier: Modifier = Modifier, artistImage: String, artistName: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier.size(120.dp).clip(RoundedCornerShape(16.dp)).clickable { },
            model = artistImage,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        DefaultText(
            modifier = Modifier,
            text = artistName,
            letterSpacing = TextUnit(-0.5F, TextUnitType.Sp),
        )
    }
}