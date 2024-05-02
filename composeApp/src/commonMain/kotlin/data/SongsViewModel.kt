package data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import audio_player.AudioPlayerListener
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

    private val _songsListFLow = MutableStateFlow<List<Song>>(
        emptyList()
    )
    val songsListFLow: StateFlow<List<Song>> get() = _songsListFLow

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> get() = _isPlaying

    private val _indexSong = MutableStateFlow(0)
    val indexSong: StateFlow<Int> get() = _indexSong

    private val _currentTime = MutableStateFlow<Float>(0F)
    val currentTime: StateFlow<Float> get() = _currentTime

    private val _fullTimeSong = MutableStateFlow<Long>(0L)
    val fullTimeSong: StateFlow<Long> get() = _fullTimeSong

    init {
        loadSongsList()
    }

    private fun loadSongsList() {
        viewModelScope.launch {
            val result = repository.getSongsList()
            _songsListFLow.value = result
            playSong()
        }
    }

    private fun playSong() {
        audioPlayerController.prepare(_songsListFLow.value[_indexSong.value].urlMusic, listener = object : AudioPlayerListener {
            override fun onReady() {
                _fullTimeSong.value = audioPlayerController.getFullTime()
            }

            override fun timeChanged(newTime: Long) {
                _currentTime.value = newTime.toFloat()
            }

            override fun onAudioFinished() {
                if (_indexSong.value < _songsListFLow.value.lastIndex) {
                    _indexSong.value++
                }
            }

            override fun onError() {
                if (_indexSong.value < _songsListFLow.value.lastIndex) {
                    _indexSong.value += 1
                }
            }
        })
    }

    fun isPlaying(): Boolean {
        return audioPlayerController.isPlaying()
    }

    fun start() {
        audioPlayerController.start()
    }

    fun pause() {
        audioPlayerController.pause()
    }

    fun pauseOrPlay() {
        if (audioPlayerController.isPlaying()) {
            audioPlayerController.pause()
            _isPlaying.value = false
        } else {
            audioPlayerController.start()
            _isPlaying.value = true
        }
    }

    fun changeTime(time: Float) {
        _currentTime.value = time
    }

    fun nextSong() {
        if (_indexSong.value == _songsListFLow.value.lastIndex) {
            _indexSong.value = 0
        } else {
            _indexSong.value++
        }
        playSong()
    }

    fun prevSong() {
        if (_indexSong.value == 0) {
            _indexSong.value = 0
        } else {
            _indexSong.value--
        }
        playSong()
    }
}