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


    private val currentBreak: ShotList
        get() = breaks.last()
}