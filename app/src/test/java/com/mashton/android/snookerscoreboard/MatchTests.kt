package com.mashton.android.snookerscoreboard
import com.mashton.android.snookerscoreboard.screens.match.LegalShot
import com.mashton.android.snookerscoreboard.screens.match.Match
import org.junit.Assert.assertEquals
import org.junit.Test

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
        return Match("foo", "bar")
    }
}