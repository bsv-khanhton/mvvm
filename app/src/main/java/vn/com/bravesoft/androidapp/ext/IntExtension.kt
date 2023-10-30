package vn.com.bravesoft.androidapp.ext

import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable
import kotlin.math.round

fun Int.toDuration(): String {
    var hour = Integer.max(0, ((toDouble() / (1000 * 60 * 60)) % 60).toInt())
    var minute = Integer.max(0, (toDouble() / (1000 * 60) % 60).toInt())
    var second = Integer.max(0, round(((toDouble() / 1000) % 60)).toInt())

    if (second >= 60) {
        minute++
        second = 0
    }

    if (minute >= 60) {
        hour++
        minute = 0
    }

    if (hour > 0) {
        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
    return String.format("%02d:%02d", minute, second)
}