package com.mashton.android.snookerscoreboard

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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
        binding.match = viewModel.match

        viewModel.apply {
            shotTicker.onChangeDo {newTicker -> binding.scoreTicker.text = newTicker }
            playerOneScore.onChangeDo {newScore -> binding.playerOneScore.text = newScore.toString() }
            playerTwoScore.onChangeDo {newScore -> binding.playerTwoScore.text = newScore.toString() }
            playerOneScoreColour.onChangeDo {colour :Int -> binding.playerOneScore.setTextColor(colour) }
            playerTwoScoreColour.onChangeDo {colour :Int -> binding.playerTwoScore.setTextColor(colour) }
            matchStartedVisibility.onChangeDo {visibility -> binding.changePlayerNamesButton.visibility = visibility }
            playerOneFrameScore.onChangeDo { newScore -> binding.playerOneFrameScore.text = newScore.formattedAsFrameScore() }
            playerTwoFrameScore.onChangeDo { newScore -> binding.playerTwoFrameScore.text = newScore.formattedAsFrameScore() }
        }
    }

    private fun Int.formattedAsFrameScore() =
        String.format(getString(R.string.frameScoreTemplate), this)

    private fun <T> LiveData<T>.onChangeDo (action: Observer<T>)
    {
        this.observe(this@MainActivity, action)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (!viewModel.processKeyPress(keyCode)) super.onKeyDown(keyCode, event)
        return true
    }


    fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater

        val dialogLayout = inflater.inflate(R.layout.player_name_change_dialog, null)

        //TODO use reflection to iterate this?
        playerOneNameEditText = dialogLayout.findViewById<EditText>(R.id.playerOneNameEditText)
        playerTwoNameEditText = dialogLayout.findViewById<EditText>(R.id.playerTwoNameEditText)

        for (player in viewModel.match.players) {
            player.nameEditText?.setText(player.name)
        }

        with(builder)
        {
            setTitle("Change Player's Name")
            setView(dialogLayout)
            setMessage("Do IT!")
            setPositiveButton("OK") { _, _ ->
                for (player in viewModel.match.players) player.name = player.nameEditText?.text.toString()
                binding.invalidateAll()
            }
            show()
        }
    }

    private val Player.nameEditText: EditText?
        get() = when (this) {
            viewModel.match.playerOne -> playerOneNameEditText
            viewModel.match.playerTwo -> playerTwoNameEditText
            else -> null
        }
}
