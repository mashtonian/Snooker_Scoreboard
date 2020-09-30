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

        assertEquals(120, testFrame.scoreFor(testFrame.currentPlayer))
    }
    @Test fun maximumGives0ScoreAfterReds() {
        val testFrame = startAFrame()

        for (x in 1..15) {
            testFrame.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        assertEquals(120, testFrame.scoreFor(testFrame.currentPlayer))
    }
    @Test fun smallBreakFollowedByFoulGivesBreakScore() {
        val testFrame = startAFrame()

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            FoulShot.FOUL_FOUR
        ))

        assertEquals(5, testFrame.scoreFor(testFrame.playerOne))
    }
    @Test fun smallBreakFollowedByFoulGivesFoulScore() {
        val testFrame = startAFrame()

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            FoulShot.FOUL_FOUR
        ))

        assertEquals(4, testFrame.scoreFor(testFrame.playerTwo))
    }
    @Test fun smallBreakFollowedByFoulGivesCorrectTurn() {
        val testFrame = startAFrame()

        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            FoulShot.FOUL_FOUR
        ))

        assertEquals(testFrame.playerTwo, testFrame.currentPlayer)
    }
    @Test fun breakContainingOpponentPenaltyContinuesCorrectly() {
        val testFrame = startAFrame()
        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            PenaltyShot.PENALTY_SIX,
            LegalShot.RED,
            LegalShot.BLACK,
            LegalShot.DOT))

        assertEquals(19, testFrame.scoreFor(testFrame.playerOne))
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
    @Test fun frameStartedFalseBeforeShotsPlayed() {
        val testFrame = startAFrame()
        assertEquals(false, testFrame.started)
    }
    @Test fun frameStartedTrueAfterShotsPlayed() {
        val testFrame = startAFrame()
        testFrame.playShot(LegalShot.DOT)
        assertEquals(true, testFrame.started)
    }

    private fun startAFrame(): Frame {
        return Frame(Player("foo"), Player("bar"))
    }

    private fun Frame.playShots(shots : Array<Shot>){
        for (shot in shots) this.playShot(shot)
    }
}