package com.mashton.android.snookerscoreboard

class Frame(val playerOne: Player, val playerTwo: Player) {

    val players = listOf(playerOne, playerTwo)
    var winner :Player? = null

    var currentPlayer = playerOne

    val nonCurrentPlayer
        get() = currentPlayer.opponent()

    fun scoreFor(player :Player): Int {
        return breaks.filter {it.player == player} .sumBy {it.score}
    }

    private val breaks: MutableList<Break> = mutableListOf(Break(currentPlayer))
    private val currentBreak: Break
        get() = breaks.last()

    private val previousBreak: Break?
        get() = breaks.getOrNull(breaks.lastIndex - 1)

    private val frameStarted
        get() = !(breaks.size == 1 && breaks.first().numberOfShots == 0 )

    val shotTicker: String
        get() = computeShotTicker()

    private fun computeShotTicker(): String {
        return breaks.joinToString(separator="")
    }

    fun finishFrame() {
        if (scoreFor(playerOne) > scoreFor(playerTwo)) winner = playerOne
        if (scoreFor(playerTwo) > scoreFor(playerOne)) winner = playerTwo
    }


    fun playShot(shot: Shot) {
        when (shot ) {
            LegalShot.DOT -> {
                currentBreak.add(shot)
                switchPlayer()
            }

            is LegalShot -> currentBreak.add(shot)
            is FoulShot -> {
                currentBreak.add(shot)
                switchPlayer()
                currentBreak.addPenaltyPoints(shot)
            }

            is PenaltyShot -> {
                currentBreak.addPenaltyPoints(shot)
            }

            ControlShot.END_OF_TURN -> {
                currentBreak.add(shot)
                switchPlayer()
            }

            ControlShot.END_OF_FRAME -> {
                currentBreak.add(shot)
            }

            ControlShot.REMOVE_LAST_SHOT -> removeLastShot()
        }
    }

    private fun removeLastShot() {
        if (frameStarted) {
            if (currentBreak.numberOfShots == 0) {
                removeCurrentBreakAndPrecedingShot()
                return
            }

            if (currentBreak.numberOfShots == 1 &&
                currentBreak.lastShot is PenaltyShot &&
                previousBreak?.lastShot is FoulShot)
            {
                removeCurrentBreakAndPrecedingShot()
                return
            }

            if (currentBreak.numberOfShots != 0) currentBreak.removeLastShot()
        }
    }

    private fun removeCurrentBreakAndPrecedingShot() {
       breaks.removeLast()
        currentPlayer = currentPlayer.opponent()!!
        currentBreak.removeLastShot()
    }

    private fun switchPlayer() {
        currentPlayer = currentPlayer.opponent()!!
        breaks.add(Break(currentPlayer))
    }

    private fun Player.opponent(): Player? = when (this) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> null
    }
}
