package com.mashton.android.snookerscoreboard
import junit.framework.Assert.assertEquals
import org.junit.Test

class ShotListTests {
    @Test
    fun maximumGives147() {
        val testShotList = ShotList()

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

    private fun ShotList.playShots(shots : Array<Shot>){
        for (shot in shots) this.add(shot)
    }

}
