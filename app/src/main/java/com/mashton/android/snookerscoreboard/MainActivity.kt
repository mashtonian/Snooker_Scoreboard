package com.mashton.android.snookerscoreboard

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_name_change_dialog.*


class MainActivity : AppCompatActivity() {

    private val match = Match(
        Player("Player One"),
        Player("Player Two")
    )


    private lateinit var scoreTicker: TextView
    private lateinit var playerOneFrameScoreView: TextView
    private lateinit var playerTwoFrameScoreView: TextView
    private lateinit var playerOneScoreView: TextView
    private lateinit var playerTwoScoreView: TextView

    private lateinit var playerOneNameView: TextView
    private lateinit var playerTwoNameView: TextView

    private lateinit var playerOneNameEditText: EditText
    private lateinit var playerTwoNameEditText: EditText

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

    fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogLayout = inflater.inflate(R.layout.player_name_change_dialog, null)

        //TODO use reflection to iterate this?
        playerOneNameEditText = dialogLayout.findViewById<EditText>(R.id.playerOneNameEditText)
        playerTwoNameEditText = dialogLayout.findViewById<EditText>(R.id.playerTwoNameEditText)

        for (player in match.players) {
            player.nameEditText?.setText(player.name)
        }

        with(builder)
        {
            setTitle("Change Player's Name")
            setView(dialogLayout)
            setMessage("Do IT!")
            setPositiveButton("OK") { _, _ ->
                for (player in match.players) player.name = player.nameEditText?.text.toString()
                displayPlayerNames()
            }
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
        displayShotTicker()
        displayScores()
        displayPlayerNames()
        setColourOfScores()
        setVisibilityOfChangeNamesButton()
    }

    private fun displayShotTicker() {
        scoreTicker.text = match.currentFrame.shotTicker
    }

    private fun displayScores() {
        for (player in match.players) {
            player.scoreView?.text = match.currentFrame.scoreFor(player).toString()
            player.frameScoreView?.text = getString(R.string.frameScoreTemplate, player.frameScore)
        }
    }

    private fun setColourOfScores() {
        match.currentFrame.currentPlayer.scoreView?.setTextColor(Color.RED)
        match.currentFrame.nonCurrentPlayer?.scoreView?.setTextColor(Color.DKGRAY)
    }

    private fun setVisibilityOfChangeNamesButton() {
        if (match.started) changePlayerNamesButton.visibility = INVISIBLE
        else changePlayerNamesButton.visibility = VISIBLE
    }

    private fun displayPlayerNames() {
        for (player in match.players) {
            player.nameView?.text = player.name
        }
    }

    //TODO refactor out the player selection code into a reusable form
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

    private val Player.nameView: TextView?
        get() = when (this) {
            match.playerOne -> playerOneNameView
            match.playerTwo -> playerTwoNameView
            else -> null
        }

    private val Player.frameScore: Int?
        get() = when (this) {
            match.playerOne -> match.playerOneFrameScore
            match.playerTwo -> match.playerTwoFrameScore
            else -> null
        }

    private val Player.nameEditText: EditText?
        get() = when (this) {
            match.playerOne -> playerOneNameEditText
            match.playerTwo -> playerTwoNameEditText
            else -> null
        }
}
