package com.mashton.android.snookerscoreboard
import junit.framework.Assert.assertEquals
import org.junit.Test

class ShotListTests {
    @Test
    fun maximumGives147() {
        val testShotList = ShotList()

        for (x in 1..15) {
            testShotList.add(LegalShot.RED)
            testShotList.add(LegalShot.BLACK)
        }

        testShotList.add(LegalShot.YELLOW)
        testShotList.add(LegalShot.GREEN)
        testShotList.add(LegalShot.BROWN)
        testShotList.add(LegalShot.BLUE)
        testShotList.add(LegalShot.PINK)
        testShotList.add(LegalShot.BLACK)
        testShotList.add(LegalShot.END_OF_FRAME)


        assertEquals(147, testShotList.score)
    }

}
