package com.mashton.android.snookerscoreboard.snooker

class Match(playerOneName: String, playerTwoName: String, val numberOfFrames: Int) {

    val playerOne = Player(playerOneName)
    val playerTwo = Player(playerTwoName)
    var winner: Player? = null
    private val frames: MutableList<Frame> = mutableListOf(Frame(playerOne, playerTwo))


    val playerOneFrameScore: Int get() = frameScoreFor(playerOne)
    val playerTwoFrameScore: Int get() = frameScoreFor(playerTwo)
    val started get() = frames.filter { it.started }.any()
    val currentFrame: Frame get() = frames.last()

    private fun endFrame() {
        when {
            playerOne.hasWonMatch -> winner = playerOne
            playerTwo.hasWonMatch -> winner = playerTwo
            else -> frames.add(Frame(playerOne, playerTwo))
        }
    }

    private fun frameScoreFor(player: Player) =
        frames.mapNotNull { it.winner }.filter { it == player }.count()

    fun playShot(shot: Shot) {
        currentFrame.playShot(shot)
        if (currentFrame.isFinished) endFrame()
    }

    private val Player.hasWonMatch get() = frameScoreFor(this) > numberOfFrames.div(2)
}

