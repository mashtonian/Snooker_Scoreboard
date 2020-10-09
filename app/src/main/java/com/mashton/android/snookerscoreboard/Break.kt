package com.mashton.android.snookerscoreboard

class Break(val player : Player) {
    private val shots: MutableList<Shot> = mutableListOf()

    val score: Int
        get() = shots.sumBy{it.value}

    val numberOfShots: Int
        get() = shots.size

    val lastShot: Shot
        get() = shots.last()

    fun add(shot: Shot) {shots.add(shot)}

    fun removeLastShot() {shots.removeLast()}

    fun addPenaltyPoints(shot : Shot){
        shots.add(
            when (shot) {
                FoulShot.FOUL_FOUR -> PenaltyShot.PENALTY_FOUR
                FoulShot.FOUL_FIVE -> PenaltyShot.PENALTY_FIVE
                FoulShot.FOUL_SIX -> PenaltyShot.PENALTY_SIX
                FoulShot.FOUL_SEVEN -> PenaltyShot.PENALTY_SEVEN
                else -> shot
            }
        )
    }

    override fun toString(): String {
        return shots.joinToString(separator = "") { it.shortName }
    }
}