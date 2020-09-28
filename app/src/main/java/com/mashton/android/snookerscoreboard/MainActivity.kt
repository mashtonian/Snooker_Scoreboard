package com.mashton.android.snookerscoreboard

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreTicker = findViewById(R.id.scoreTicker)

        playerOneScoreView = findViewById(R.id.playerOneScore)
        playerTwoScoreView = findViewById(R.id.playerTwoScore)
        playerOneFrameScoreView = findViewById(R.id.playerOneFrameScore)
        playerTwoFrameScoreView = findViewById(R.id.playerTwoFrameScore)
        playerOneNameView = findViewById(R.id.playerOneName)
        playerTwoNameView = findViewById(R.id.playerTwoName)

        updateUiElements()
    }

    private val match = Match(
        Player("Player One"),
        Player("Player Two"))


    private lateinit var scoreTicker: TextView
    private lateinit var playerOneFrameScoreView: TextView
    private lateinit var playerTwoFrameScoreView: TextView
    private lateinit var playerOneScoreView: TextView
    private lateinit var playerTwoScoreView: TextView
    private lateinit var playerOneNameView: TextView
    private lateinit var playerTwoNameView: TextView

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val mappingResult = KeyPressShotMapper.map(keyCode)
        mappingResult.shot?.play()
        if (!mappingResult.handled) super.onKeyDown(keyCode, event)
        return true
    }

    private fun Shot.play() {
        match.playShot(this)
        updateUiElements()
    }

    private fun updateUiElements() {
        scoreTicker.text = match.currentFrame.shotTicker

        for (player in match.currentFrame.players) {
            player.findScoreViewByPlayer()?.text = match.currentFrame.scoreFor(player).toString()
        }

        playerOneFrameScoreView.text = "(" + match.playerOneFrameScore + ")"
        playerTwoFrameScoreView.text = "(" + match.playerTwoFrameScore + ")"

        match.currentFrame.currentPlayer.findScoreViewByPlayer()?.setTextColor(Color.RED)
        match.currentFrame.nonCurrentPlayer?.findScoreViewByPlayer()?.setTextColor(Color.DKGRAY)
    }

    private fun Player.findScoreViewByPlayer(): TextView? {
        return when (this) {
            match.currentFrame.playerOne -> playerOneScoreView
            match.currentFrame.playerTwo -> playerTwoScoreView
            else -> null
        }
    }
}