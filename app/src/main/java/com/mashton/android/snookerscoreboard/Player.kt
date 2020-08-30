package com.mashton.android.snookerscoreboard

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.stream.Collectors

class Player {
    var shots = ArrayList<Shot>()
    var breakShots = ArrayList<Shot>()

    fun endBreak() {
        shots.addAll(breakShots)
    }

    fun playShot(shot: Shot) {
        breakShots.add(shot)
    }

    fun receivePenaltyPoints(shot: Shot){
        shots.add(shot)
    }

    val breakScore: Int
        @RequiresApi(Build.VERSION_CODES.N)
        get() = breakShots.stream().collect(Collectors.summingInt(Shot::value))

    val score: Int
        @RequiresApi(Build.VERSION_CODES.N)
        get() = shots.stream().collect(Collectors.summingInt(Shot::value))
}