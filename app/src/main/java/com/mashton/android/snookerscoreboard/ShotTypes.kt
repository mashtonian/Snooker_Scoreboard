package com.mashton.android.snookerscoreboard

interface Shot {val value: Int}

enum class LegalShot(override val value: Int) : Shot {
    DOT(0), RED(1),
    YELLOW(2), GREEN(3), BROWN(4),BLUE(5), PINK(6), BLACK(7),
    END_OF_FRAME(0)
}

enum class IllegalShot(override val value: Int) : Shot {
    FOUL_FOUR(0), FOUL_FIVE(0), FOUL_SIX(0), FOUL_SEVEN(0), MISS(0),
    PENALTY_FOUR(4), PENALTY_FIVE(5), PENALTY_SIX(6), PENALTY_SEVEN(7)
}
