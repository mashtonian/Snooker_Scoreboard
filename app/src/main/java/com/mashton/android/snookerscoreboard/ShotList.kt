package com.mashton.android.snookerscoreboard

class ShotList {
    private val shots: MutableList<Shot> = mutableListOf()

    val score: Int
        get() = shots.sumBy{it.value}

    val numberOfShots: Int
        get() = shots.size

    val lastShot: Shot
        get() = shots.last()

    fun add(shot: Shot) {shots.add(shot)}

    override fun toString(): String {
        return shots.joinToString(separator = "") { it.shortName }
    }

    fun removeLastShot() {
        shots.removeLast()
    }
}