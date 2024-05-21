package ui.player

sealed class PlayerEvents {
    object PauseSong : PlayerEvents()
    object ResumeSong : PlayerEvents()
    object NextSong : PlayerEvents()
    object PrevSong : PlayerEvents()
    data class ChangeTime(val time: Float) : PlayerEvents()
    data class ScrollToSong(val indexSong: Int) : PlayerEvents()
    object ChangeRepeatMode : PlayerEvents()
    object ChangeShuffleMode : PlayerEvents()
}