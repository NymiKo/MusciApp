package utils

import java.util.concurrent.TimeUnit

fun Long.toMinutes(): Long {
    return TimeUnit.MILLISECONDS.toMinutes(this)
}

fun Long.toSeconds(): Long {
    return if (TimeUnit.MILLISECONDS.toSeconds(this) % 60 > 9) (TimeUnit.MILLISECONDS.toSeconds(this) % 60)
    else (TimeUnit.MILLISECONDS.toSeconds(this) % 60)
}