package com.mashton.android.snookerscoreboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val redButton: Button = findViewById(R.id.buttonRed)
        redButton.setOnClickListener {playShot(LegalShot.RED)}

        val blackButton: Button = findViewById(R.id.buttonBlack)
        blackButton.setOnClickListener {playShot(LegalShot.BLACK)}

        val penaltyFiveButton: Button = findViewById(R.id.buttonPen5)
        penaltyFiveButton.setOnClickListener {playShot(IllegalShot.PENALTY_FIVE)}

        val dotButton: Button = findViewById(R.id.buttonDot)
        dotButton.setOnClickListener {playShot(LegalShot.DOT)}

        scoreTicker = findViewById(R.id.scoreTicker)
        playerOneScore = findViewById(R.id.playerOneScore)
        playerTwoScore = findViewById(R.id.playerTwoScore)

    }

    private val frame = Frame()

    private lateinit var scoreTicker: TextView
    private lateinit var playerOneScore: TextView
    private lateinit var playerTwoScore: TextView

    var fPressedForFoul = false
    var pPressedForPenalty = false

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var foul = fPressedForFoul
        fPressedForFoul = false

        var penalty = pPressedForPenalty
        pPressedForPenalty = false
        return when (keyCode) {
            KeyEvent.KEYCODE_1 -> {
                playShot(LegalShot.RED)
                true
            }

            KeyEvent.KEYCODE_2 -> {
                playShot(LegalShot.YELLOW)
                true
            }

            KeyEvent.KEYCODE_3 -> {
                playShot(LegalShot.GREEN)
                true
            }

            KeyEvent.KEYCODE_4 -> {
                if(foul) playShot(IllegalShot.FOUL_FOUR)
                if(penalty) playShot(IllegalShot.PENALTY_FOUR)
                if(!foul && !penalty) playShot(LegalShot.BROWN)
                true
            }

            KeyEvent.KEYCODE_5 -> {
                if(foul) playShot(IllegalShot.FOUL_FIVE)
                if(penalty) playShot(IllegalShot.PENALTY_FIVE)
                if(!foul && !penalty) playShot(LegalShot.BLUE)
                true
            }

            KeyEvent.KEYCODE_6 -> {
                if(foul) playShot(IllegalShot.FOUL_SIX)
                if(penalty) playShot(IllegalShot.PENALTY_SIX)
                if(!foul && !penalty) playShot(LegalShot.PINK)
                true
            }

            KeyEvent.KEYCODE_7 -> {
                if(foul) playShot(IllegalShot.FOUL_SEVEN)
                if(penalty) playShot(IllegalShot.PENALTY_SEVEN)
                if(!foul && !penalty) playShot(LegalShot.BLACK)
                true
            }

            KeyEvent.KEYCODE_PERIOD -> {
                playShot(LegalShot.DOT)
                true
            }

            KeyEvent.KEYCODE_F -> {
                fPressedForFoul = true
                true
            }

            KeyEvent.KEYCODE_P -> {
                pPressedForPenalty = true
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }

    private fun playShot(shot: Shot) {
        frame.playShot(shot)
        scoreTicker.text = frame.shotTicker
        playerOneScore.text = frame.playerOne.score.toString()
        playerTwoScore.text = frame.playerTwo.score.toString()

    }
}