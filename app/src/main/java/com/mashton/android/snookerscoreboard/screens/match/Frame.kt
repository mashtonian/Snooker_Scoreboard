package com.mashton.android.snookerscoreboard.screens.match

class Frame(val playerOne: Player, val playerTwo: Player) {

    val players = listOf(playerOne, playerTwo)
    var winner : Player? = null
    var currentPlayer = playerOne
    private val breaks: MutableList<Break> = mutableListOf(Break(currentPlayer))

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
        if (this.started) {
            when {
                lastShotWasAFoul.or(lastShotWasAPenalty)  -> {
                    removeCurrentBreakAndPrecedingShot()
                    return
                }

                lastShotWasLegal -> currentBreak.removeLastShot ()
            }
        }
    }

    private val lastShotWasLegal get() = currentBreak.numberOfShots != 0

    private val lastShotWasAPenalty get() = currentBreak.numberOfShots == 0

    private val lastShotWasAFoul get() =
        currentBreak.numberOfShots == 1 && currentBreak.lastShot is PenaltyShot && previousBreak?.lastShot is FoulShot

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

    private val Player.score get() = breaks.filter {it.player == this} .sumBy {it.score}

    val nonCurrentPlayer
        get() = currentPlayer.opponent()

    fun scoreFor(player : Player): Int {
        return player.score
    }

    private val currentBreak: Break
        get() = breaks.last()

    private val previousBreak: Break?
        get() = breaks.getOrNull(breaks.lastIndex - 1)

    val started
        get() = !(breaks.size == 1 && breaks.first().numberOfShots == 0 )

    val shotTicker: String
        get() = breaks.joinToString(separator="")

    fun finishFrame() {
        when {
            playerOne.score > playerTwo.score -> winner = playerOne
            playerTwo.score > playerOne.score -> winner = playerTwo

            //TODO: Implement re-spotted black end to frame, when scores are tied
        }
    }
}
