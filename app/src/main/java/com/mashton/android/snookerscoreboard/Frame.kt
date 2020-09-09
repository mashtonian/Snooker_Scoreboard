package com.mashton.android.snookerscoreboard

import kotlin.text.StringBuilder

class Frame {

    val playerOne = Player()
    val playerTwo = Player()

    var currentPlayer = playerOne
    private val playerWhoBrokeOff = currentPlayer

    var shotTicker: String = ""
        get() = computeShotTicker()

    private fun computeShotTicker(): String {
        val tickerBuilder = StringBuilder()
        for (i in 0 until playerWhoBrokeOff.breaks.size) {
            tickerBuilder.append(playerWhoBrokeOff.breaks[i].toString())
            tickerBuilder.append(playerWhoBrokeOff.opponent().breaks.getOrElse(i) { ShotList() }
                .toString())
        }
        return tickerBuilder.toString()
    }

    fun playShot(shot: Shot) {
        currentPlayer.playShot(shot)
        controlTurnFlow(shot)
    }

    private fun controlTurnFlow(shot: Shot) {
        when (shot) {
            LegalShot.DOT, LegalShot.END_OF_TURN -> {
                switchPlayer()
            }

            LegalShot.END_OF_FRAME -> currentPlayer.endBreak()

            IllegalShot.FOUL_FOUR,
            IllegalShot.FOUL_FIVE,
            IllegalShot.FOUL_SIX,
            IllegalShot.FOUL_SEVEN -> {
                currentPlayer.opponent().receivePenaltyPoints(shot as IllegalShot)
                switchPlayer()
            }
        }
    }

    private fun switchPlayer() {
        currentPlayer.endBreak()
        currentPlayer = currentPlayer.opponent()
    }

    private fun Player.opponent(): Player = when (this) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> Player()
    }


}