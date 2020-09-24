package com.mashton.android.snookerscoreboard
import org.junit.Test
import org.junit.Assert.*

class FrameTests {

    @Test fun maximumGives120BreakAfterReds() {
        val testFrame = startAFrame()

        for (x in 1..15) {
            testFrame.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        assertEquals(120, testFrame.currentPlayer.breakScore)
    }
    @Test fun maximumGives0ScoreAfterReds() {
        val testFrame = startAFrame()

        for (x in 1..15) {
            testFrame.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        assertEquals(120, testFrame.currentPlayer.score)
    }
    @Test fun smallBreakFollowedByFoulGivesBreakScore() {
        val testFrame = startAFrame()

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR ))

        assertEquals(5, testFrame.playerOne.score)
    }
    @Test fun smallBreakFollowedByFoulGivesFoulScore() {
        val testFrame = startAFrame()

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR ))

        assertEquals(4, testFrame.playerTwo.score)
    }
    @Test fun smallBreakFollowedByFoulGivesCorrectTurn() {
        val testFrame = startAFrame()

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.FOUL_FOUR ))

        assertEquals(testFrame.playerTwo, testFrame.currentPlayer)
    }
    @Test fun breakContainingOpponentPenaltyContinuesCorrectly() {
        val testFrame = startAFrame()
        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            IllegalShot.PENALTY_SIX,
            LegalShot.RED,
            LegalShot.BLACK,
            LegalShot.DOT))

        assertEquals(19, testFrame.playerOne.score)
    }
    @Test fun shotTickerCorrectAfterEqualNumberOfBreaksByEachPlayer() {
        val testFrame = startAFrame()

        for(x in 1..4) {
            testFrame.playShots(
                arrayOf(
                    LegalShot.RED,
                    LegalShot.BLACK,
                    LegalShot.DOT
                )
            )
        }

        assertEquals("17.17.17.17.", testFrame.shotTicker)
    }
    @Test fun shotTickerCorrectAfterUnequalNumberOfBreaksByEachPlayer() {
        val testFrame = startAFrame()

        for(x in 1..5) {
            testFrame.playShots(
                arrayOf(
                    LegalShot.RED,
                    LegalShot.BLACK,
                    LegalShot.DOT
                )
            )
        }

        assertEquals("17.17.17.17.17.", testFrame.shotTicker)
    }

    private fun startAFrame(): Frame {
        return Frame()
    }

    private fun Frame.playShots(shots : Array<Shot>){
        for (shot in shots) this.playShot(shot)
    }
}