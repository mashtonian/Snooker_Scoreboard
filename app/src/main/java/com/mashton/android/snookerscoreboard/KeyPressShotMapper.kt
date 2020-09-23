package com.mashton.android.snookerscoreboard

import android.view.KeyEvent

class KeyPressShotMapper {

    private var fPressedForFoul = false
    private var pPressedForPenalty = false

    fun map (keyCode: Int): KeyPressMappingResult
    {
        val foul = fPressedForFoul
        fPressedForFoul = false

        val penalty = pPressedForPenalty
        pPressedForPenalty = false

        val result: KeyPressMappingResult

        result = when (keyCode) {
            KeyEvent.KEYCODE_PERIOD -> KeyPressMappingResult(true, LegalShot.DOT)
            KeyEvent.KEYCODE_1 -> KeyPressMappingResult(true, LegalShot.RED)
            KeyEvent.KEYCODE_2 -> KeyPressMappingResult(true, LegalShot.YELLOW)
            KeyEvent.KEYCODE_3 -> KeyPressMappingResult(true, LegalShot.GREEN)

            KeyEvent.KEYCODE_4 -> {
                when {
                    foul -> KeyPressMappingResult(true, IllegalShot.FOUL_FOUR)
                    penalty -> KeyPressMappingResult(true, IllegalShot.PENALTY_FOUR)
                    else -> KeyPressMappingResult(true, LegalShot.BROWN) } }

            KeyEvent.KEYCODE_5 -> {
                when {
                    foul -> KeyPressMappingResult(true, IllegalShot.FOUL_FIVE)
                    penalty -> KeyPressMappingResult(true, IllegalShot.PENALTY_FIVE)
                    else -> KeyPressMappingResult(true, LegalShot.BLUE) } }

            KeyEvent.KEYCODE_6 -> {
                when {
                    foul -> KeyPressMappingResult(true, IllegalShot.FOUL_SIX)
                    penalty -> KeyPressMappingResult(true, IllegalShot.PENALTY_SIX)
                    else -> KeyPressMappingResult(true, LegalShot.PINK) } }

            KeyEvent.KEYCODE_7 -> {
                when {
                    foul -> KeyPressMappingResult(true, IllegalShot.FOUL_SEVEN)
                    penalty -> KeyPressMappingResult(true, IllegalShot.PENALTY_SEVEN)
                    else -> KeyPressMappingResult(true, LegalShot.BLACK) } }

            KeyEvent.KEYCODE_F -> {
                fPressedForFoul = true
                KeyPressMappingResult(true, null)
            }

            KeyEvent.KEYCODE_P -> {
                pPressedForPenalty = true
                KeyPressMappingResult(true, null) }

            else -> KeyPressMappingResult(false, null)
        }
        return result
    }

}