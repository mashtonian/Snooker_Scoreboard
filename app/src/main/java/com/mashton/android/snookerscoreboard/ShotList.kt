package com.mashton.android.snookerscoreboard

class ShotList {
    var shots = ArrayList<Shot>()

    val score: Int
        get() = shots.sumBy{it.value}

    fun add(shot: Shot)
    {
        shots.add(shot)
    }

    fun add(newShots: ArrayList<Shot>)
    {
        shots.addAll(newShots)
    }

}