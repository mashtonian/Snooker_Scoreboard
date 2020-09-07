package com.mashton.android.snookerscoreboard

class ShotList {
    var shots: MutableList<Shot> = mutableListOf()

    val score: Int
        get() = shots.sumBy{it.value}

    fun add(shot: Shot) {shots.add(shot)}
    fun add(newShots: MutableList<Shot>) {shots.addAll(newShots)}

    override fun toString(): String {
        return shots.joinToString(separator = "") { it.shortName }
    }
}