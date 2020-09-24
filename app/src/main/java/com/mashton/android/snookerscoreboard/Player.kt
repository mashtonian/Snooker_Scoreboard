package com.mashton.android.snookerscoreboard

import android.widget.TextView

class Player {
    var breaks: MutableList<ShotList> = mutableListOf()

    val score: Int
        get() = breaks.sumOf { it.score }

    val breakScore: Int
        get() = currentBreak.score

    fun startNewBreak() { breaks.add(ShotList()) }

    fun playShot(shot: Shot) { currentBreak.add(shot) }

    fun receivePenaltyPoints(shot: IllegalShot){
        addPenaltyPoints(shot)
        startNewBreak()
    }

    private fun addPenaltyPoints(shot: IllegalShot) {
        breaks.last().add(
            when (shot) {
                IllegalShot.FOUL_FOUR -> IllegalShot.PENALTY_FOUR
                IllegalShot.FOUL_FIVE -> IllegalShot.PENALTY_FIVE
                IllegalShot.FOUL_SIX -> IllegalShot.PENALTY_SIX
                IllegalShot.FOUL_SEVEN -> IllegalShot.PENALTY_SEVEN
                else -> shot
            }
        )
    }

    private val currentBreak: ShotList
        get() = breaks.last()
}