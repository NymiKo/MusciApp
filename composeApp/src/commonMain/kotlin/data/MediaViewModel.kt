package data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import audio_player.AudioPlayerController
import audio_player.AudioPlayerListener
import data.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MediaViewModel(
    private val repository: MediaRepository,
    private val audioPlayerController: AudioPlayerController
) : ViewModel() {

    private val _songsListFLow = MutableStateFlow<List<Song>>(
        emptyList()
    )
    val songsListFLow: StateFlow<List<Song>> get() = _songsListFLow

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> get() = _isPlaying

    private val _currentPlayingSongIndex = MutableStateFlow(0)
    val currentPlayingSongIndex: StateFlow<Int> get() = _currentPlayingSongIndex

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
            playSong(result)
        }
    }

    private fun playSong(songsList: List<Song>) {
        audioPlayerController.prepare(songsList, listener = object : AudioPlayerListener {
            override fun onReady() {
                _fullTimeSong.value = audioPlayerController.getFullTime()
                viewModelScope.launch {
                    audioPlayerController.getCurrentTime().collect {
                        _currentTime.value = it.toFloat()
                    }
                }
            }

            override fun onAudioChanged(indexSong: Int) {
                _currentPlayingSongIndex.value = indexSong
            }

            override fun onError() {
                if (_currentPlayingSongIndex.value < _songsListFLow.value.lastIndex) {
                    _currentPlayingSongIndex.value += 1
                }
            }
        })
    }

    fun isPlaying(): Boolean {
        return audioPlayerController.isPlaying()
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
        if (_currentPlayingSongIndex.value == _songsListFLow.value.lastIndex) {
            _currentPlayingSongIndex.value = 0
        } else {
            _currentPlayingSongIndex.value++
            audioPlayerController.changeSong(_currentPlayingSongIndex.value)
        }
    }

    fun prevSong() {
        if (_currentPlayingSongIndex.value == 0) {
            _currentPlayingSongIndex.value = 0
        } else {
            _currentPlayingSongIndex.value--
            audioPlayerController.changeSong(_currentPlayingSongIndex.value)
        }
    }

    fun scrollToSong(indexSong: Int) {
        _currentPlayingSongIndex.value = indexSong
        audioPlayerController.changeSong(indexSong)
    }

    fun releasePlayer() {
        audioPlayerController.release()
    }
}