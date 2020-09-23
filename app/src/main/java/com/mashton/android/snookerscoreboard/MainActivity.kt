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
        mappingResult.shot?.play()
        if (!mappingResult.handled) super.onKeyDown(keyCode, event)
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