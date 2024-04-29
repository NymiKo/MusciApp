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
        listOf(
            Song(
                "LE SSERAFIM",
                "EASY",
                "http://f0862137.xsph.ru/images/LE_SSERAFIM_-_EASY_77468865.mp3",
                "https://static.wikia.nocookie.net/kpop/images/2/23/LE_SSERAFIM_Easy_digital_cover.png/revision/latest?cb=20240219121927&path-prefix=ru"
            ),
            Song(
                "Stray Kids",
                "LALALA",
                "http://f0862137.xsph.ru/images/Stray_Kids_-_LALALA_76943246.mp3",
                "https://static.wikia.nocookie.net/stray-kids/images/5/5f/LALALALA_MV_gif.gif/revision/latest?cb=20231112234310"
            ),
            Song(
                "Ariana Grande",
                "7 rings",
                "http://f0862137.xsph.ru/images/Ariana_Grande_-_7_rings_61522389.mp3",
                "https://static.wikia.nocookie.net/arianagrande/images/e/ef/7_Rings_Cover.jpg/revision/latest/scale-to-width-down/1000?cb=20190206061540"
            )
        )
    )
    val songsListFLow: StateFlow<List<Song>> get() = _songsListFLow

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> get() = _isPlaying

    private var indexSong: Int = 2
    private val _currentTime = MutableStateFlow<Float>(0F)
    val currentTime: StateFlow<Float> get() = _currentTime

    init {
        loadSongsList()
    }

    private fun loadSongsList() {
        viewModelScope.launch {
            val result = repository.getSongsList()
            playSong()
            //_songsListFLow.value = result
        }
    }

    private fun playSong() {
        audioPlayerController.prepare(_songsListFLow.value[indexSong].urlMusic, listener = object : AudioPlayerListener {
            override fun onReady() {

            }

            override fun timeChanged(newTime: Long) {
                _currentTime.value = newTime.toFloat()
            }

            override fun onAudioFinished() {
                if (indexSong < _songsListFLow.value.lastIndex) {
                    indexSong += 1
                }
            }

            override fun onError() {
                if (indexSong < _songsListFLow.value.lastIndex) {
                    indexSong += 1
                }
            }
        })
    }

    fun getFullTime(): Long {
        return audioPlayerController.getFullTime()
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
}