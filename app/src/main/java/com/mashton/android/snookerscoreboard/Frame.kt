package com.mashton.android.snookerscoreboard

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    var currentPlayer = playerTwo

    fun playShot(shot: Shot) {
        currentPlayer.playShot(shot)
        controlTurnFlow(shot)
        }

    private fun controlTurnFlow(shot: Shot) {
        when (shot) {
            LegalShot.DOT -> {
                currentPlayer.endBreak()
                currentPlayer = otherPlayer()
            }
            LegalShot.END_OF_FRAME -> currentPlayer.endBreak()

            IllegalShot.FOUL_FOUR,
            IllegalShot.FOUL_FIVE,
            IllegalShot.FOUL_SIX,
            IllegalShot.FOUL_SEVEN -> {
                otherPlayer().receivePenaltyPoints(shot as IllegalShot)
                currentPlayer.endBreak()
                currentPlayer = otherPlayer()
            }
        }
    }

    private fun otherPlayer(): Player = when (currentPlayer) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> Player()
    }
}