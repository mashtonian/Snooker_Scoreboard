package com.mashton.android.snookerscoreboard

class Frame {

    val playerOne = Player()
    val playerTwo = Player()
    var currentPlayer = playerOne
    var shotTicker: String = ""

    fun playShot(shot: Shot) {
        currentPlayer.playShot(shot)
        shotTicker += shot.shortName
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
                otherPlayer().receivePenaltyPoints(shot as IllegalShot)
                switchPlayer()
            }
        }
    }

    private fun switchPlayer() {
        currentPlayer.endBreak()
        currentPlayer = otherPlayer()
    }

    private fun otherPlayer(): Player = when (currentPlayer) {
        playerOne -> playerTwo
        playerTwo -> playerOne
        else -> Player()
    }


}