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

    private val match = Match()

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
        match.currentFrame.playShot(this)
        updateUiElements()
    }

    private fun updateUiElements() {
        scoreTicker.text = match.currentFrame.shotTicker
        for (player in match.currentFrame.players) {
            player.findScoreViewByPlayer()?.text = player.score.toString()
        }
        match.currentFrame.currentPlayer.findScoreViewByPlayer()?.setTextColor(Color.RED)
        match.currentFrame.nonCurrentPlayer.findScoreViewByPlayer()?.setTextColor(Color.DKGRAY)
    }

    private fun Player.findScoreViewByPlayer(): TextView? {
        return when (this) {
            match.currentFrame.playerOne -> playerOneScoreView
            match.currentFrame.playerTwo -> playerTwoScoreView
            else -> null
        }
    }
}