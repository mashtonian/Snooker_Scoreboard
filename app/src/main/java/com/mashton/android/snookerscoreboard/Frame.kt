package com.mashton.android.snookerscoreboard

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    val players = listOf(playerOne, playerTwo)

    var currentPlayer = playerOne
    val nonCurrentPlayer
        get() = currentPlayer.opponent()

    private val playerWhoBrokeOff = currentPlayer

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
        currentPlayer.playShot(shot)
        controlCurrentPlayerAfterThis(shot)
    }

    private fun controlCurrentPlayerAfterThis(shot: Shot) {
        when (shot) {
            LegalShot.DOT, LegalShot.END_OF_TURN -> {
                switchPlayer()
            }

            LegalShot.DOT, LegalShot.END_OF_TURN,
            IllegalShot.FOUL_FOUR,
            IllegalShot.FOUL_FIVE,
            IllegalShot.FOUL_SIX,
            IllegalShot.FOUL_SEVEN -> {
                switchPlayer()
                currentPlayer.receivePenaltyPoints(shot as IllegalShot)
            }
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
