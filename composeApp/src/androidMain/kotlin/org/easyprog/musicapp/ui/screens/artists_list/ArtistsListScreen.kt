package org.easyprog.musicapp.ui.screens.artists_list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.easyprog.musicapp.ui.screens.artists_list.uicomponents.ArtistsList
import org.easyprog.musicapp.ui.screens.player.uicomponents.TopAppBarCustom
import ui.artists_list.ArtistsListScreenUiState

@Composable
fun ArtistsListScreen(
    modifier: Modifier = Modifier,
    uiState: ArtistsListScreenUiState,
    onArtistSongsList: (idArtist: Long, nameArtist: String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBarCustom(
                title = "Список исполнителей",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                backgroundColor = MaterialTheme.colorScheme.background,
                onBack = onBack::invoke
            )
        }
    ) { innerPadding ->
        ArtistsList(
            modifier = modifier.padding(innerPadding),
            artistsList = uiState.artistsList,
            onArtistSongsList = onArtistSongsList::invoke
        )
    }
}