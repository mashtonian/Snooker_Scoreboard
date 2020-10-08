package com.mashton.android.snookerscoreboard.screens.match

class Match(playerOneName :String, playerTwoName :String, numberOfFrames :Int){

    val playerOne = Player(playerOneName)
    val playerTwo = Player(playerTwoName)
    val numberOfFrames = numberOfFrames
    private val frames: MutableList<Frame> = mutableListOf(Frame(playerOne, playerTwo))
    val currentFrame: Frame
        get() = frames.last()

    val started
        get() = frames.filter{it.started}.any()

    val playerOneFrameScore: Int
        get() = frameScoreFor(playerOne)

    val playerTwoFrameScore: Int
        get() = frameScoreFor(playerTwo)

    private fun frameScoreFor(player : Player) =
        frames.mapNotNull { it.winner }.filter {it == player}.count()

    fun playShot(shot: Shot) {
        currentFrame.playShot(shot)
        if (currentFrame.isFinished) startNextFrame()
    }

    private fun startNextFrame() {
        frames.add(Frame(playerOne, playerTwo))
    }
}