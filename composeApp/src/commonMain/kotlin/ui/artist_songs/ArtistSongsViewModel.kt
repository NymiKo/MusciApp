package ui.artist_songs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class ArtistSongsViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var artistSongsUiState by mutableStateOf(
        ArtistSongsScreenUiState(
            idArtist = checkNotNull(savedStateHandle["idArtist"]),
            nameArtist = checkNotNull(savedStateHandle["nameArtist"])
        )
    )
        private set
}