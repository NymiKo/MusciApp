package org.easyprog.musicapp.ui.screens.artist_songs

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.easyprog.musicapp.ui.screens.artist_songs.uicomponents.ArtistSongsListComponent
import org.easyprog.musicapp.ui.screens.artist_songs.uicomponents.LargeTopAppBarCustom
import ui.artist_songs.ArtistSongsScreenUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistSongsScreen(
    modifier: Modifier = Modifier,
    uiState: ArtistSongsScreenUiState,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Box(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).navigationBarsPadding().nestedScroll(scrollBehavior.nestedScrollConnection)) {
        if (orientationScreen()) {
            AsyncImage(
                modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp),
                model = uiState.imageArtist,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp).background(Color(0x41121212)))
        }
        Column(modifier = Modifier.fillMaxSize()) {
            LargeTopAppBarCustom(title = uiState.nameArtist, scrollBehavior = scrollBehavior, onBack = onBack::invoke)
            ArtistSongsListComponent(songsList = uiState.songsList)
        }
    }
}

@Composable
private fun orientationScreen(): Boolean {
    return LocalConfiguration.current.orientation != Configuration.ORIENTATION_LANDSCAPE
}