package org.easyprog.musicapp.extension

import androidx.media3.common.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun Player.currentPositionFlow(
    updateFrequency: Duration = 1.seconds
) = flow {
    while (true) {
        if (isPlaying) emit(currentPosition)
        if (playbackState == Player.STATE_READY) emit(currentPosition)
        delay(updateFrequency)
    }
}.flowOn(Dispatchers.Main)