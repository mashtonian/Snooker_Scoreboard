package com.mashton.android.snookerscoreboard

class Player {
    var breaks: MutableList<ShotList> = mutableListOf(ShotList())

    val score: Int
        get() = breaks.sumOf { it.score }

    val breakScore: Int
        get() = currentBreak.score

    fun endBreak() {    breaks.add(ShotList())
    }

    fun playShot(shot: Shot) { currentBreak.add(shot) }

    fun receivePenaltyPoints(shot: IllegalShot){
        breaks.last().add(when (shot){
            IllegalShot.FOUL_FOUR -> IllegalShot.PENALTY_FOUR
            IllegalShot.FOUL_FIVE -> IllegalShot.PENALTY_FIVE
            IllegalShot.FOUL_SIX -> IllegalShot.PENALTY_SIX
            IllegalShot.FOUL_SEVEN -> IllegalShot.PENALTY_SEVEN
            else -> shot
        })
    }

    val currentBreak: ShotList
        get() = breaks.last()
}