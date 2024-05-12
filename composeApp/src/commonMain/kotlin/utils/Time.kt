package utils

import java.util.concurrent.TimeUnit

fun Long.toTimeString(): String {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(this)
    val seconds = if (TimeUnit.MILLISECONDS.toSeconds(this) % 60 > 9) (TimeUnit.MILLISECONDS.toSeconds(this) % 60)
    else (TimeUnit.MILLISECONDS.toSeconds(this) % 60)
    return "$minutes:${seconds.toString().padStart(2, '0')}"
}