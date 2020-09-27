package com.mashton.android.snookerscoreboard

import android.widget.TextView

class Player {
    val breaks: MutableList<ShotList> = mutableListOf()

    val score: Int
        get() = breaks.sumOf { it.score }

    val breakScore: Int?
        get() = currentBreak?.score

    val numberOfShotsInCurrentBreak: Int?
        get() = currentBreak?.numberOfShots

    fun startNewBreak() { breaks.add(ShotList()) }

    fun playShot(shot: Shot) { currentBreak?.add(shot) }
    fun removeLastShot() {currentBreak?.removeLastShot()}

    fun receivePenaltyPoints(shot :Shot){
        breaks.last().add(
            when (shot) {
                FoulShot.FOUL_FOUR -> PenaltyShot.PENALTY_FOUR
                FoulShot.FOUL_FIVE -> PenaltyShot.PENALTY_FIVE
                FoulShot.FOUL_SIX -> PenaltyShot.PENALTY_SIX
                FoulShot.FOUL_SEVEN -> PenaltyShot.PENALTY_SEVEN
                else -> shot
            }
        )
    }

    fun removeCurrentBreak() {
        breaks.removeLast()
    }

    val currentBreak: ShotList?
        get() = breaks.lastOrNull()
}