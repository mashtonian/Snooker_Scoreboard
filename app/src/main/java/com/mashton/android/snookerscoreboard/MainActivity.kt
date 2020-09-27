package com.mashton.android.snookerscoreboard

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreTicker = findViewById(R.id.scoreTicker)

        playerOneScoreView = findViewById(R.id.playerOneScore)
        playerTwoScoreView = findViewById(R.id.playerTwoScore)
        playerOneNameView = findViewById(R.id.playerOneName)
        playerTwoNameView = findViewById(R.id.playerTwoName)

        updateUiElements()
    }

    private val frame = Frame()

    private lateinit var scoreTicker: TextView
    private lateinit var playerOneScoreView: TextView
    private lateinit var playerTwoScoreView: TextView
    private lateinit var playerOneNameView: TextView
    private lateinit var playerTwoNameView: TextView

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
            player.findScoreViewByPlayer()?.text = player.score.toString()
        }
        frame.currentPlayer.findScoreViewByPlayer()?.setTextColor(Color.RED)
        frame.nonCurrentPlayer.findScoreViewByPlayer()?.setTextColor(Color.DKGRAY)
    }

    private fun Player.findScoreViewByPlayer(): TextView? {
        return when (this) {
            frame.playerOne -> playerOneScoreView
            frame.playerTwo -> playerTwoScoreView
            else -> null
        }
    }
}