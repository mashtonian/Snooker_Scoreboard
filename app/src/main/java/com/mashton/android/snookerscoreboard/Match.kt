package com.mashton.android.snookerscoreboard

class Match {
    private val frames: MutableList<Frame> = mutableListOf(Frame())
    val currentFrame = frames.last()
}