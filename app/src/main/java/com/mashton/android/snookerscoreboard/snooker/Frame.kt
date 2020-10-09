package com.mashton.android.snookerscoreboard.snooker

class Frame(val playerOne: Player, val playerTwo: Player) {

    var winner : Player? = null
    var currentPlayer = playerOne
    private val breaks: MutableList<Break> = mutableListOf(Break(currentPlayer))
    var isFinished :Boolean = false

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
                if (scoresAreEqual) breaks.add(Break(currentPlayer))
                else finishFrame()
            }

            ControlShot.REMOVE_LAST_SHOT -> removeLastShot()
        }
    }

    private fun removeLastShot() {
        if (this.started) {
            when {
                lastShotWasAFoul.or(lastShotWasAPenalty)  -> {
                    removeCurrentBreakAndPrecedingShot()
                    return }

                lastShotWasLegal -> currentBreak.removeLastShot ()
            }
        }
    }

    private val scoresAreEqual get() = (scoreFor(playerOne) == scoreFor(playerTwo))

    private val Player.score get() = breaks.filter {it.player == this} .sumBy {it.score}

    private val previousBreak get() = breaks.getOrNull(breaks.lastIndex - 1)

    private val currentBreak get() = breaks.last()

    private val lastShotWasLegal get() = currentBreak.numberOfShots != 0

    private val lastShotWasAPenalty get() = currentBreak.numberOfShots == 0

    private val lastShotWasAFoul get() =
        currentBreak.numberOfShots == 1 &&
                currentBreak.lastShot is PenaltyShot &&
                previousBreak?.lastShot is FoulShot

    val started get() = !(breaks.size == 1 && breaks.first().numberOfShots == 0 )

    val shotTicker: String get() = breaks.joinToString(separator="")

    fun scoreFor(player : Player) = player.score

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

    private fun finishFrame() {
        when {
            playerOne.score > playerTwo.score -> winner = playerOne
            playerTwo.score > playerOne.score -> winner = playerTwo
        }

        this.isFinished = true
    }
}
