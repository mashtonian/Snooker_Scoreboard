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

    fun receivePenaltyPoints(shot: IllegalShot){
        shots.add(when (shot){
            IllegalShot.FOUL_FOUR -> IllegalShot.PENALTY_FOUR
            IllegalShot.FOUL_FIVE -> IllegalShot.PENALTY_FIVE
            IllegalShot.FOUL_SIX -> IllegalShot.PENALTY_SIX
            IllegalShot.FOUL_SEVEN -> IllegalShot.PENALTY_SEVEN
            else -> shot
        })
    }

    val breakScore: Int
        @RequiresApi(Build.VERSION_CODES.N)
        get() = breakShots.stream().collect(Collectors.summingInt(Shot::value))

    val score: Int
        @RequiresApi(Build.VERSION_CODES.N)
        get() = shots.stream().collect(Collectors.summingInt(Shot::value))
}