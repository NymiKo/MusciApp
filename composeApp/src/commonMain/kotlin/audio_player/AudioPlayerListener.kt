package audio_player

import data.model.Song

interface AudioPlayerListener {
    fun onReady()
    fun onAudioChanged(indexSong: Int)
    fun onError()
}