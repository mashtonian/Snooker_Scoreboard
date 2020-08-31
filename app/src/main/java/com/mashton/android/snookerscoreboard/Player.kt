package com.mashton.android.snookerscoreboard

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.stream.Collectors

class Player {
    var shots = ShotList()
    var score: Int = 0
        get() = shots.score

    var breakShots = ShotList()
    var breakScore: Int = 0
        get() = breakShots.score

    fun endBreak() {
        shots.add(breakShots.shots)
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
}