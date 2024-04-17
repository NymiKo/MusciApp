package data

import data.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SongsViewModel(
    private val repository: SongsRepository
): ViewModel() {

    init {
        loadSongsList()
    }

    private val _songsListFLow = MutableStateFlow<List<Song>>(emptyList())
    val songsListFLow: StateFlow<List<Song>> get() = _songsListFLow

    private fun loadSongsList() {
        viewModelScope.launch {
            val result = repository.getSongsList()
            _songsListFLow.tryEmit(result)
        }
    }

}