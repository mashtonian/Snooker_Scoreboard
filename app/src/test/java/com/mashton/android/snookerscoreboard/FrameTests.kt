package com.mashton.android.snookerscoreboard
import org.junit.Assert.assertEquals
import org.junit.Test

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
    @Test fun frameFinishedIsTrueAfterEofPlayedAndScoresAreNotEqual() {
        val testFrame = startAFrame()
        testFrame.playShots(
            arrayOf(
                LegalShot.RED,
                LegalShot.BLACK,
                LegalShot.DOT,
                LegalShot.RED,
                ControlShot.END_OF_FRAME
            ))
        assertEquals(true, testFrame.isFinished)
        assertEquals(testFrame.playerOne, testFrame.winner)
    }
    @Test fun frameFinishedIsFalseAfterEofPlayedAndScoresAreEqual() {
        val testFrame = startAFrame()
        testFrame.playShots(
            arrayOf(
                LegalShot.RED,
                LegalShot.BLACK,
                LegalShot.DOT,
                LegalShot.RED,
                LegalShot.BLACK,
                ControlShot.END_OF_FRAME
            ))
        assertEquals(false, testFrame.isFinished)
    }
    @Test fun removeAShotDoesNothingWhenNoShotsHaveBeenPlayed() {
        val testFrame = startAFrame()
        testFrame.playShot(ControlShot.REMOVE_LAST_SHOT)
    }
    @Test fun canCorrectlyRemoveAScoringShot() {
        val testFrame = startAFrame()
        testFrame.playShots(arrayOf (
            LegalShot.RED,
            LegalShot.BROWN,
            ControlShot.REMOVE_LAST_SHOT
        ))

        val breaks = testFrame.getPrivateProperty<Frame, MutableList<Break>>("breaks")
        assertEquals(1, breaks?.size)
        assertEquals(1, breaks?.last()?.numberOfShots)
        assertEquals(LegalShot.RED, breaks?.last()?.lastShot)
    }
    @Test fun canCorrectlyRemoveAFoulShot() {
        val testFrame = startAFrame()
        testFrame.playShots(arrayOf (
            LegalShot.RED,
            FoulShot.FOUL_FOUR,
            ControlShot.REMOVE_LAST_SHOT
        ))

        val breaks = testFrame.getPrivateProperty<Frame, MutableList<Break>>("breaks")
        assertEquals(1, breaks?.size)
        assertEquals(1, breaks?.last()?.numberOfShots)
        assertEquals(LegalShot.RED, breaks?.last()?.lastShot)
    }
    @Test fun canCorrectlyRemoveAPenaltyShot() {
        val testFrame = startAFrame()
        testFrame.playShots(arrayOf (
            LegalShot.RED,
            PenaltyShot.PENALTY_SEVEN,
            ControlShot.REMOVE_LAST_SHOT
        ))

        val breaks = testFrame.getPrivateProperty<Frame, MutableList<Break>>("breaks")
        assertEquals(1, breaks?.size)
        assertEquals(1, breaks?.last()?.numberOfShots)
        assertEquals(LegalShot.RED, breaks?.last()?.lastShot)
    }

    private fun startAFrame(): Frame {
        return Frame(Player("foo"), Player("bar"))
    }
    private fun Frame.playShots(shots : Array<Shot>){
        for (shot in shots) this.playShot(shot)
    }
}

