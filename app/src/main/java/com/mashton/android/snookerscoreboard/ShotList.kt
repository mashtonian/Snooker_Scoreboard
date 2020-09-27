package com.mashton.android.snookerscoreboard

class ShotList {
    private var shots: MutableList<Shot> = mutableListOf()

    val score: Int
        get() = shots.sumBy{it.value}

    val numberOfShots: Int
        get() =shots.size

    fun add(shot: Shot) {shots.add(shot)}

    override fun toString(): String {
        return shots.joinToString(separator = "") { it.shortName }
    }

    fun removeLastShot() {
        shots.removeLast()
    }
}