package com.mashton.android.snookerscoreboard

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.stream.Collectors

class Frame {
    var shots = ArrayList<Shot>()

    fun playShot(shot: Shot) {
        shots.add(shot)
    }

    val score
        @RequiresApi(Build.VERSION_CODES.N)
        get() = shots.stream().collect(Collectors.summingInt(Shot::value))
}