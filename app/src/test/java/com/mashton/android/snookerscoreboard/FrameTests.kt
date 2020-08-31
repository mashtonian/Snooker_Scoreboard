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

    @Test
    fun smallBreakFollowedByFoulGivesBreakScore() {
        val testFrame = Frame()

        testFrame.currentPlayer = testFrame.playerOne

        testFrame.playShot(LegalShot.RED)
        testFrame.playShot(LegalShot.BROWN)
        testFrame.playShot(IllegalShot.FOUL_FOUR)
        assertEquals(5, testFrame.playerOne.score)
    }

    @Test
    fun smallBreakFollowedByFoulGivesFoulScore() {
        val testFrame = Frame()

        testFrame.currentPlayer = testFrame.playerOne

        testFrame.playShot(LegalShot.RED)
        testFrame.playShot(LegalShot.BROWN)
        testFrame.playShot(IllegalShot.FOUL_FOUR)
        assertEquals(4, testFrame.playerTwo.score)
    }

    @Test
    fun smallBreakFollowedByFoulGivesCorrectTurn() {
        val testFrame = Frame()

        testFrame.currentPlayer = testFrame.playerOne

        playShots(testFrame, arrayOf(
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR
        ))

        assertEquals(testFrame.playerTwo, testFrame.currentPlayer)
    }

    private fun playShots(frame : Frame, shots : Array<Shot>){
        for (shot in shots) frame.playShot(shot)
    }
}