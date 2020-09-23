package com.mashton.android.snookerscoreboard

import android.graphics.Color
import kotlin.text.StringBuilder

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    val players = listOf(playerOne, playerTwo)

    var currentPlayer = playerOne
    private val playerWhoBrokeOff = currentPlayer

    val shotTicker: String
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
        controlCurrentPlayerAfterThis(shot)
        currentPlayer.playShot(shot)
    }

    private fun controlCurrentPlayerAfterThis(shot: Shot) {
        when (shot) {
            LegalShot.DOT, LegalShot.END_OF_TURN,
            IllegalShot.FOUL_FOUR,
            IllegalShot.FOUL_FIVE,
            IllegalShot.FOUL_SIX,
            IllegalShot.FOUL_SEVEN->  switchPlayer()

            LegalShot.END_OF_FRAME -> currentPlayer.endBreak()
        }
    }

    private fun switchPlayer() {
        currentPlayer.endBreak()
        currentPlayer.scoreView.setTextColor(Color.DKGRAY)
        currentPlayer = currentPlayer.opponent()
        currentPlayer.scoreView.setTextColor(Color.RED)
    }

    private fun Player.opponent(): Player = when (this) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> Player()
    }


}
