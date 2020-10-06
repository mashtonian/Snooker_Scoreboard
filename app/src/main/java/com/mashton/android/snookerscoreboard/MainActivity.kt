package com.mashton.android.snookerscoreboard

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mashton.android.snookerscoreboard.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var playerOneNameEditText: EditText
    private lateinit var playerTwoNameEditText: EditText

    private lateinit var binding :ActivityMainBinding
    private val viewModel: MatchViewModel
            by viewModels {MatchViewModelFactory(
                getString(R.string.PlayerOneName),
                getString(R.string.PlayerTwoName))}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.match = viewModel.match.value
        binding.currentFrame = viewModel.match.value?.currentFrame

        updateUiElements()
    }

    fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogLayout = inflater.inflate(R.layout.player_name_change_dialog, null)

        //TODO use reflection to iterate this?
        playerOneNameEditText = dialogLayout.findViewById<EditText>(R.id.playerOneNameEditText)
        playerTwoNameEditText = dialogLayout.findViewById<EditText>(R.id.playerTwoNameEditText)

        for (player in viewModel.match.value?.players!!) {
            player.nameEditText?.setText(player.name)
        }

        with(builder)
        {
            setTitle("Change Player's Name")
            setView(dialogLayout)
            setMessage("Do IT!")
            setPositiveButton("OK") { _, _ ->
                for (player in viewModel.match.value?.players!!) player.name = player.nameEditText?.text.toString()
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
        viewModel.match.value?.playShot(this)
        updateUiElements()
    }

    private fun updateUiElements() {
        binding.currentFrame = viewModel.match.value?.currentFrame
        setColourOfScores()
        setVisibilityOfChangeNamesButton()
        binding.invalidateAll()
    }

    private fun setColourOfScores() {
        viewModel.match.value?.currentFrame?.currentPlayer?.scoreView?.setTextColor(Color.RED)
        viewModel.match.value?.currentFrame?.nonCurrentPlayer?.scoreView?.setTextColor(Color.DKGRAY)
    }

    private fun setVisibilityOfChangeNamesButton() {
        if (viewModel.match.value?.started!!) binding.changePlayerNamesButton.visibility = INVISIBLE
        else binding.changePlayerNamesButton.visibility = VISIBLE
    }

    //TODO refactor out the player selection code into a reusable form
    private val Player.scoreView: TextView?
        get() = when (this) {
            viewModel.match.value?.playerOne -> binding.playerOneScore
            viewModel.match.value?.playerTwo -> binding.playerTwoScore
            else -> null
        }

    private val Player.nameEditText: EditText?
        get() = when (this) {
            viewModel.match.value?.playerOne -> playerOneNameEditText
            viewModel.match.value?.playerTwo -> playerTwoNameEditText
            else -> null
        }
}
