package com.mashton.android.snookerscoreboard
import org.junit.Test
import org.junit.Assert.*

class FrameTests {
    @Test fun maximumGives120BreakAfterReds() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        assertEquals(120, testFrame.currentPlayer.breakScore)
    }
    @Test fun maximumGives0ScoreAfterReds() {
        val testFrame = Frame()

        for (x in 1..15) {
            testFrame.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        assertEquals(0, testFrame.currentPlayer.score)
    }
    @Test fun smallBreakFollowedByFoulGivesBreakScore() {
        val testFrame = Frame()

        testFrame.currentPlayer = testFrame.playerOne

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR ))

        assertEquals(5, testFrame.playerOne.score)
    }
    @Test fun smallBreakFollowedByFoulGivesFoulScore() {
        val testFrame = Frame()

        testFrame.currentPlayer = testFrame.playerOne

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR ))

        assertEquals(4, testFrame.playerTwo.score)
    }
    @Test fun smallBreakFollowedByFoulGivesCorrectTurn() {
        val testFrame = Frame()

        testFrame.currentPlayer = testFrame.playerOne

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR ))

        assertEquals(testFrame.playerTwo, testFrame.currentPlayer)
    }

    private fun Frame.playShots(shots : Array<Shot>){
        for (shot in shots) this.playShot(shot)
    }
}