package com.mashton.android.snookerscoreboard

import android.graphics.Color
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
        redButton.setOnClickListener {LegalShot.RED.play()}

        val blackButton: Button = findViewById(R.id.buttonBlack)
        blackButton.setOnClickListener {LegalShot.BLACK.play()}

        val penaltyFiveButton: Button = findViewById(R.id.buttonPen5)
        penaltyFiveButton.setOnClickListener {IllegalShot.PENALTY_FIVE.play()}

        val dotButton: Button = findViewById(R.id.buttonDot)
        dotButton.setOnClickListener {LegalShot.DOT.play()}

        scoreTicker = findViewById(R.id.scoreTicker)

        frame.playerOne.scoreView = findViewById(R.id.playerOneScore)
        frame.playerTwo.scoreView = findViewById(R.id.playerTwoScore)

        frame.playerOne.scoreView.setTextColor(Color.RED)
        frame.playerTwo.scoreView.setTextColor(Color.DKGRAY)
    }

    private val frame = Frame()

    private lateinit var scoreTicker: TextView
    private val keyPressShotMapper = KeyPressShotMapper()

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val mappingResult = keyPressShotMapper.map(keyCode)
        mappingResult?.shotToPlay?.play()
        if (mappingResult == null) super.onKeyDown(keyCode, event)
        return true
    }


    private fun Shot.play() {
        frame.playShot(this)
        updateUiElements()
    }

    private fun updateUiElements() {
        scoreTicker.text = frame.shotTicker
        for (player in frame.players) {
            player.scoreView.text = player.score.toString()
        }
    }
}