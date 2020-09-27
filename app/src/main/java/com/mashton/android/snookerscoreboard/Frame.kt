package com.mashton.android.snookerscoreboard

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    val players = listOf(playerOne, playerTwo)
    private lateinit var winner :Player

    var currentPlayer = playerOne
    val nonCurrentPlayer
        get() = currentPlayer.opponent()

    private val frameStarted
        get() = computeShotTicker() != ""

    init { currentPlayer.startNewBreak() }

    val shotTicker: String
        get() = computeShotTicker()

    private fun computeShotTicker(): String {
        val mergedListOfBreaks = with(setOf(playerOne.breaks, playerTwo.breaks).sortedByDescending { it.count() }) {
            first().mapIndexed { index, e ->
                listOfNotNull(e, last().getOrNull(index))
            }
        }.flatten()

        return mergedListOfBreaks.joinToString(separator="")
    }

    fun playShot(shot: Shot) {
        when (shot ) {
            LegalShot.DOT -> {
                currentPlayer.playShot(shot)
                switchPlayer()
            }

            is LegalShot -> currentPlayer.playShot(shot)
            is FoulShot -> {
                currentPlayer.playShot(shot)
                switchPlayer()
                currentPlayer.receivePenaltyPoints(shot)
            }

            is PenaltyShot -> {
                currentPlayer.receivePenaltyPoints(shot)
            }

            ControlShot.END_OF_TURN
            -> {
                currentPlayer.playShot(shot)
                switchPlayer()
            }

            ControlShot.END_OF_FRAME -> finishFrame()

            ControlShot.REMOVE_LAST_SHOT -> removeLastShot()
        }
    }

    private fun finishFrame() {
        if (playerOne.score > playerTwo.score) winner = playerOne
        if (playerTwo.score > playerOne.score) winner = playerTwo
    }


    private fun removeLastShot() {
        if (frameStarted) {
            if (currentPlayer.numberOfShotsInCurrentBreak == 0) {
                removeCurrentBreakAndPreceedingShot()
                return
            }

            if (currentPlayer.numberOfShotsInCurrentBreak == 1 &&
                currentPlayer.currentBreak?.lastShot is PenaltyShot &&
                nonCurrentPlayer.currentBreak?.lastShot is FoulShot)
            {
                removeCurrentBreakAndPreceedingShot()
                return
            }

            if (currentPlayer.numberOfShotsInCurrentBreak != 0) currentPlayer.removeLastShot()
        }
    }

    private fun removeCurrentBreakAndPreceedingShot() {
        currentPlayer.removeCurrentBreak()
        currentPlayer = currentPlayer.opponent()
        currentPlayer.removeLastShot()
    }

    private fun switchPlayer() {
        currentPlayer = currentPlayer.opponent()
        currentPlayer.startNewBreak()
    }

    private fun Player.opponent(): Player = when (this) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> Player()
    }
}
