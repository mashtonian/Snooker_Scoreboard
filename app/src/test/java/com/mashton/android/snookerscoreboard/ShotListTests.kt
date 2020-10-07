package com.mashton.android.snookerscoreboard
import com.mashton.android.snookerscoreboard.screens.match.*
import junit.framework.Assert.assertEquals
import org.junit.Test

class ShotListTests {
    @Test fun maximumGivesScoreOf147() {
        val testShotList = Break(Player("foo"))

        for (x in 1..15) {
            testShotList.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        testShotList.playShots(arrayOf (
            LegalShot.YELLOW,
            LegalShot.GREEN,
            LegalShot.BROWN,
            LegalShot.BLUE,
            LegalShot.PINK,
            LegalShot.BLACK ))

        assertEquals(147, testShotList.score)
    }
    @Test fun maximumGivesCorrectToString() {
        val testShotList = Break(Player("foo"))

        for (x in 1..15) {
            testShotList.playShots(arrayOf (
                LegalShot.RED,
                LegalShot.BLACK ))
        }

        testShotList.playShots(arrayOf (
            LegalShot.YELLOW,
            LegalShot.GREEN,
            LegalShot.BROWN,
            LegalShot.BLUE,
            LegalShot.PINK,
            LegalShot.BLACK,
            ControlShot.END_OF_FRAME
        ))

        assertEquals("171717171717171717171717171717234567EOF",
            testShotList.toString())
    }

    private fun Break.playShots(shots : Array<Shot>){
        for (shot in shots) this.add(shot)
    }

}
