package com.mashton.android.snookerscoreboard

class Player {
    var shots = ShotList()

    val score: Int
        get() = shots.score

    var breakShots = ShotList()
    val breakScore: Int
        get() = breakShots.score

    fun endBreak() {    shots.add(breakShots.shots)
                        breakShots.shots.clear()
    }
    fun playShot(shot: Shot) { breakShots.add(shot) }

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