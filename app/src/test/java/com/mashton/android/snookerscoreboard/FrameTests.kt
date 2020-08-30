package com.mashton.android.snookerscoreboard

import org.junit.Test

import org.junit.Assert.*

class FrameTests {
    @Test
    fun maximumGives147() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShot(LegalShot.RED)
            testFrame.playShot(LegalShot.BLACK)
        }

        testFrame.playShot(LegalShot.YELLOW)
        testFrame.playShot(LegalShot.GREEN)
        testFrame.playShot(LegalShot.BROWN)
        testFrame.playShot(LegalShot.BLUE)
        testFrame.playShot(LegalShot.PINK)
        testFrame.playShot(LegalShot.BLACK)
        testFrame.playShot(LegalShot.END_OF_FRAME)


        assertEquals(147, testFrame.currentPlayer.score)
    }

    @Test
    fun maximumGives120BreakAfterReds() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShot(LegalShot.RED)
            testFrame.playShot(LegalShot.BLACK)
        }

        assertEquals(120, testFrame.currentPlayer.breakScore)

        testFrame.playShot(LegalShot.YELLOW)
        testFrame.playShot(LegalShot.GREEN)
        testFrame.playShot(LegalShot.BROWN)
        testFrame.playShot(LegalShot.BLUE)
        testFrame.playShot(LegalShot.PINK)
        testFrame.playShot(LegalShot.BLACK)
        testFrame.playShot(LegalShot.END_OF_FRAME)
    }

    @Test
    fun maximumGives0ScoreAfterReds() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShot(LegalShot.RED)
            testFrame.playShot(LegalShot.BLACK)
        }

        assertEquals(0, testFrame.currentPlayer.score)

        testFrame.playShot(LegalShot.YELLOW)
        testFrame.playShot(LegalShot.GREEN)
        testFrame.playShot(LegalShot.BROWN)
        testFrame.playShot(LegalShot.BLUE)
        testFrame.playShot(LegalShot.PINK)
        testFrame.playShot(LegalShot.BLACK)
        testFrame.playShot(LegalShot.END_OF_FRAME)
    }
}