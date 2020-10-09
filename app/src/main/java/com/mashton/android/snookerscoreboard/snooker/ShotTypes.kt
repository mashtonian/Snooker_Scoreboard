package com.mashton.android.snookerscoreboard.snooker

interface Shot{
    val value: Int
    val shortName: String
}

enum class LegalShot(override val value: Int, override val shortName: String) : Shot {
    DOT(0, "."), RED(1, "1"),
    YELLOW(2, "2"), GREEN(3, "3"),
    BROWN(4, "4"),BLUE(5, "5"),
    PINK(6, "6"), BLACK(7, "7")
}

enum class FoulShot(override val value: Int, override val shortName: String) : Shot {
    FOUL_FOUR(0, "F4"), FOUL_FIVE(0, "F5"),
    FOUL_SIX(0, "F6"), FOUL_SEVEN(0, "F7")
}

enum class PenaltyShot(override val value: Int, override val shortName: String) : Shot {
    PENALTY_FOUR(4, "P4"), PENALTY_FIVE(5, "P5"),
    PENALTY_SIX(6, "P6"), PENALTY_SEVEN(7, "P7")
}

enum class ControlShot(override val value: Int, override val shortName: String) : Shot {
    REMOVE_LAST_SHOT(0, ""), END_OF_TURN(0, "EOT"), END_OF_FRAME(0, "EOF")
}
