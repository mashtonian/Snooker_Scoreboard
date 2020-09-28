package com.mashton.android.snookerscoreboard

class Match(val playerOne: Player, val playerTwo: Player){

    val players = listOf(playerOne, playerTwo)
    private val frames: MutableList<Frame> = mutableListOf(Frame(playerOne, playerTwo))
    val currentFrame: Frame
        get() = frames.last()

    val playerOneFrameScore: Int
        get() = frameScoreFor(playerOne)

    val playerTwoFrameScore: Int
        get() = frameScoreFor(playerTwo)

    private fun frameScoreFor(player :Player) = frames.map{it.winner}.filterNotNull().filter {it == player}.count()

    fun playShot(shot: Shot) {
        currentFrame.playShot(shot)
        if (shot == ControlShot.END_OF_FRAME) {
            currentFrame.finishFrame()
            frames.add(Frame(playerOne, playerTwo))
        }
    }
}