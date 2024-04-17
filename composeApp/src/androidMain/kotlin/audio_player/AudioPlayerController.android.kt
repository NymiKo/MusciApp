package audio_player

import audio_player.AudioPlayerListener

actual class AudioPlayerController actual constructor() {
    actual fun prepare(url: String, listener: AudioPlayerListener) {
    }

    actual fun start() {
    }

    actual fun pause() {
    }

    actual fun stop() {
    }

    actual fun isPlaying(): Boolean {
        return false
    }

    actual fun release() {
    }
}