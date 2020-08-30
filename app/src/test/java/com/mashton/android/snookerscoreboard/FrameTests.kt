package com.mashton.android.snookerscoreboard

import org.junit.Test

import org.junit.Assert.*

class FrameTests {
    @Test
    fun maximumGives147() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShot(Shot.RED)
            testFrame.playShot(Shot.BLACK)
        }

        testFrame.playShot(Shot.YELLOW)
        testFrame.playShot(Shot.GREEN)
        testFrame.playShot(Shot.BROWN)
        testFrame.playShot(Shot.BLUE)
        testFrame.playShot(Shot.PINK)
        testFrame.playShot(Shot.BLACK)
        testFrame.playShot(Shot.END_OF_FRAME)


        assertEquals(147, testFrame.currentPlayer.score)
    }

    @Test
    fun maximumGives120BreakAfterReds() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShot(Shot.RED)
            testFrame.playShot(Shot.BLACK)
        }

        assertEquals(120, testFrame.currentPlayer.breakScore)

        testFrame.playShot(Shot.YELLOW)
        testFrame.playShot(Shot.GREEN)
        testFrame.playShot(Shot.BROWN)
        testFrame.playShot(Shot.BLUE)
        testFrame.playShot(Shot.PINK)
        testFrame.playShot(Shot.BLACK)
        testFrame.playShot(Shot.END_OF_FRAME)
    }

    @Test
    fun maximumGives0ScoreAfterReds() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShot(Shot.RED)
            testFrame.playShot(Shot.BLACK)
        }

        assertEquals(0, testFrame.currentPlayer.score)

        testFrame.playShot(Shot.YELLOW)
        testFrame.playShot(Shot.GREEN)
        testFrame.playShot(Shot.BROWN)
        testFrame.playShot(Shot.BLUE)
        testFrame.playShot(Shot.PINK)
        testFrame.playShot(Shot.BLACK)
        testFrame.playShot(Shot.END_OF_FRAME)
    }
}