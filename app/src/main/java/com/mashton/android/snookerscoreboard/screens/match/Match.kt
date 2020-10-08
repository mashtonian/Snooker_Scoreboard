package com.mashton.android.snookerscoreboard.screens.match

class Match(playerOneName :String, playerTwoName :String, val numberOfFrames: Int){

    val playerOne = Player(playerOneName)
    val playerTwo = Player(playerTwoName)
    private val frames: MutableList<Frame> = mutableListOf(Frame(playerOne, playerTwo))


    val playerOneFrameScore: Int get() = frameScoreFor(playerOne)
    val playerTwoFrameScore: Int get() = frameScoreFor(playerTwo)
    val started get() = frames.filter{it.started}.any()
    val currentFrame: Frame get() = frames.last()

    private fun startNextFrame() = frames.add(Frame(playerOne, playerTwo))

    private fun frameScoreFor(player : Player) =
        frames.mapNotNull { it.winner }.filter {it == player}.count()

    fun playShot(shot: Shot) {
        currentFrame.playShot(shot)
        if (currentFrame.isFinished) startNextFrame()
    }


}