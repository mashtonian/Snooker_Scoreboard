package com.mashton.android.snookerscoreboard

import android.widget.TextView

class Player {
    var breaks: MutableList<ShotList> = mutableListOf(ShotList())
    lateinit var scoreView: TextView

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