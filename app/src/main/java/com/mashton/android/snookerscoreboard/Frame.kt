package com.mashton.android.snookerscoreboard

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    val players = listOf(playerOne, playerTwo)

    var currentPlayer = playerOne
    val nonCurrentPlayer
        get() = currentPlayer.opponent()

    init {currentPlayer.startNewBreak()}

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
            is IllegalShot -> {
                currentPlayer.playShot(shot)
                switchPlayer()
                currentPlayer.receivePenaltyPoints(shot)
            }
            ControlShot.END_OF_TURN -> {
                currentPlayer.playShot(shot)
                switchPlayer()
            }
            ControlShot.REMOVE_LAST_SHOT -> removeLastShot()
        }
    }

    private fun removeLastShot() {
        if (currentPlayer.numberOfShotsInCurrentBreak != 0) currentPlayer.removeLastShot()
        else {
            currentPlayer.removeCurrentBreak()
            currentPlayer = currentPlayer.opponent()
            if (currentPlayer.numberOfShotsInCurrentBreak != 0) currentPlayer.removeLastShot()
        }
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
