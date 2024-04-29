package data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import data.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongsViewModel(
    private val repository: SongsRepository,
    private val audioPlayerController: AudioPlayerController
) : ViewModel() {

    private val _songsListFLow = MutableStateFlow<List<Song>>(emptyList())
    val songsListFLow: StateFlow<List<Song>> get() = _songsListFLow

    init {
        loadSongsList()
    }

    private fun loadSongsList() {
        viewModelScope.launch {
            val result = repository.getSongsList()
            _songsListFLow.tryEmit(result)
        }
    }
}