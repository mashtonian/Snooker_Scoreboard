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
import androidx.databinding.DataBindingUtil
import com.mashton.android.snookerscoreboard.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.player_name_change_dialog.*


class MainActivity : AppCompatActivity() {

    private val match = Match(
        Player("Player One"),
        Player("Player Two")
    )

    private lateinit var playerOneNameEditText: EditText
    private lateinit var playerTwoNameEditText: EditText

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.match = match
        binding.currentFrame = match.currentFrame

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
                binding.invalidateAll()
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
        binding.currentFrame = match.currentFrame
        setColourOfScores()
        setVisibilityOfChangeNamesButton()
        binding.invalidateAll()
    }

    private fun setColourOfScores() {
        match.currentFrame.currentPlayer.scoreView?.setTextColor(Color.RED)
        match.currentFrame.nonCurrentPlayer?.scoreView?.setTextColor(Color.DKGRAY)
    }

    private fun setVisibilityOfChangeNamesButton() {
        if (match.started) binding.changePlayerNamesButton.visibility = INVISIBLE
        else binding.changePlayerNamesButton.visibility = VISIBLE
    }

    //TODO refactor out the player selection code into a reusable form
    private val Player.scoreView: TextView?
        get() = when (this) {
            match.playerOne -> binding.playerOneScore
            match.playerTwo -> binding.playerTwoScore
            else -> null
        }

    private val Player.nameEditText: EditText?
        get() = when (this) {
            match.playerOne -> playerOneNameEditText
            match.playerTwo -> playerTwoNameEditText
            else -> null
        }
}
