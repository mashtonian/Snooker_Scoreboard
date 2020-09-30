package com.mashton.android.snookerscoreboard
import org.junit.Test
import org.junit.Assert.*

class MatchTests {

    @Test fun matchStartedFalseBeforeShotsPlayed() {
        val testMatch = startAMatch()
        assertEquals(false, testMatch.started)
    }
    @Test fun matchStartedTrueAfterShotsPlayed() {
        val testMatch = startAMatch()
        testMatch.playShot(LegalShot.DOT)
        assertEquals(true, testMatch.started)
    }

    private fun startAMatch(): Match {
        return Match(Player("foo"), Player("bar"))
    }
}