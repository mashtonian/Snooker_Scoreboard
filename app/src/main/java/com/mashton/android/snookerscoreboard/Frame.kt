package com.mashton.android.snookerscoreboard

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.stream.Collectors

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    var currentPlayer = playerTwo

    fun playShot(shot: Shot) {
        currentPlayer.playShot(shot)
        when (shot) {
            Shot.DOT -> {
                currentPlayer.endBreak()
                currentPlayer = nextPlayer() }

            Shot.END_OF_FRAME -> currentPlayer.endBreak()
        }
    }

    private fun nextPlayer(): Player = when (currentPlayer) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> Player()
    }
}