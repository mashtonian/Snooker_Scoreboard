package com.mashton.android.snookerscoreboard

import android.view.KeyEvent

data class KeyMapperResult(var shot :Shot?, var handled :Boolean)

class KeyPressShotMapper {

    private var fPressedForFoul = false
    private var pPressedForPenalty = false

    fun map (keyCode: Int): KeyMapperResult {

        //ignore spurious NumLock keypresses for my shonky BlueTooth numpad
        if (keyCode==KeyEvent.KEYCODE_NUM_LOCK) return KeyMapperResult(null, false)

        val foul = fPressedForFoul
        fPressedForFoul = false

        val penalty = pPressedForPenalty
        pPressedForPenalty = false

        var handled = true
        val shot :Shot? = when (keyCode) {
            KeyEvent.KEYCODE_PERIOD, KeyEvent.KEYCODE_NUMPAD_DOT -> LegalShot.DOT
            KeyEvent.KEYCODE_1, KeyEvent.KEYCODE_NUMPAD_1 -> LegalShot.RED
            KeyEvent.KEYCODE_2, KeyEvent.KEYCODE_NUMPAD_2 -> LegalShot.YELLOW
            KeyEvent.KEYCODE_3, KeyEvent.KEYCODE_NUMPAD_3 -> LegalShot.GREEN
            KeyEvent.KEYCODE_T, KeyEvent.KEYCODE_NUMPAD_ADD -> ControlShot.END_OF_TURN
            KeyEvent.KEYCODE_DEL -> ControlShot.REMOVE_LAST_SHOT

            KeyEvent.KEYCODE_4, KeyEvent.KEYCODE_NUMPAD_4 -> {
                when {
                    foul -> IllegalShot.FOUL_FOUR
                    penalty -> IllegalShot.PENALTY_FOUR
                    else -> LegalShot.BROWN } }

            KeyEvent.KEYCODE_5, KeyEvent.KEYCODE_NUMPAD_5 -> {
                when {
                    foul -> IllegalShot.FOUL_FIVE
                    penalty -> IllegalShot.PENALTY_FIVE
                    else -> LegalShot.BLUE } }

            KeyEvent.KEYCODE_6, KeyEvent.KEYCODE_NUMPAD_6 -> {
                when {
                    foul -> IllegalShot.FOUL_SIX
                    penalty -> IllegalShot.PENALTY_SIX
                    else -> LegalShot.PINK } }

            KeyEvent.KEYCODE_7, KeyEvent.KEYCODE_NUMPAD_7 -> {
                when {
                    foul -> IllegalShot.FOUL_SEVEN
                    penalty -> IllegalShot.PENALTY_SEVEN
                    else -> LegalShot.BLACK } }

            KeyEvent.KEYCODE_F, KeyEvent.KEYCODE_NUMPAD_MULTIPLY -> {
                fPressedForFoul = true
                null }

            KeyEvent.KEYCODE_P, KeyEvent.KEYCODE_NUMPAD_DIVIDE -> {
                pPressedForPenalty = true
                null }

            else -> {
                handled = false
                null
            }
        }

        return KeyMapperResult(shot, handled)
    }
}