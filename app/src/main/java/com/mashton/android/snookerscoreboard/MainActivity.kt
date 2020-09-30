package com.mashton.android.snookerscoreboard

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

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

    fun showAlertDialog(view: View)
    {val builder = AlertDialog.Builder(this)

        with(builder)
        {
            setTitle("Change Player's Name")
            setMessage("Do IT!")
            setPositiveButton("OK") { _, _ -> }
            show()
        }
    }

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

        for (player in match.players) {
            player.scoreView?.text = match.currentFrame.scoreFor(player).toString()
            player.frameScoreView?.text = getString(R.string.frameScoreTemplate, player.frameScore)
        }

        match.currentFrame.currentPlayer.scoreView?.setTextColor(Color.RED)
        match.currentFrame.nonCurrentPlayer?.scoreView?.setTextColor(Color.DKGRAY)

        if (match.started) changePlayerNamesButton.visibility = INVISIBLE
        else changePlayerNamesButton.visibility = VISIBLE
    }

    private val Player.scoreView: TextView?
        get() = when (this) {
            match.playerOne -> playerOneScoreView
            match.playerTwo -> playerTwoScoreView
            else -> null
        }

    private val Player.frameScoreView: TextView?
        get() = when (this) {
            match.playerOne -> playerOneFrameScoreView
            match.playerTwo -> playerTwoFrameScoreView
            else -> null
        }

    private val Player.frameScore: Int?
            get() = when (this) {
                match.playerOne -> match.playerOneFrameScore
                match.playerTwo -> match.playerTwoFrameScore
                else -> null
            }
}
