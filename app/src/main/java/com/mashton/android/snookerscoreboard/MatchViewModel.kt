package com.mashton.android.snookerscoreboard

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mashton.android.snookerscoreboard.screens.match.KeyPressShotMapper

class MatchViewModel(playerOneName :String, playerTwoName :String, numberOfFrames :Int) : ViewModel() {

    val match = Match(playerOneName, playerTwoName, numberOfFrames)

    private val _shotTicker = MutableLiveData<String>()
    val shotTicker: LiveData<String> get() = _shotTicker

    private val _playerOneScore = MutableLiveData<Int>()
    val playerOneScore: LiveData<Int> get() = _playerOneScore

    private val _playerTwoScore = MutableLiveData<Int>()
    val playerTwoScore: LiveData<Int> get() = _playerTwoScore

    private val _playerOneFrameScore = MutableLiveData<Int>()
    val playerOneFrameScore: LiveData<Int> get() = _playerOneFrameScore

    private val _playerTwoFrameScore = MutableLiveData<Int>()
    val playerTwoFrameScore: LiveData<Int> get() = _playerTwoFrameScore

    private val _finished = MutableLiveData<Boolean>()
    val finished: LiveData<Boolean> get() = _finished

    private val _currentPlayer = MutableLiveData<Player>()

    val playerOneScoreColour = colourFor(match.playerOne)
    val playerTwoScoreColour = colourFor(match.playerTwo)

    private fun colourFor (player : Player): LiveData<Int> {
        return Transformations.map(_currentPlayer) { currentPlayer ->
            if (currentPlayer == player) Color.RED else Color.DKGRAY }
    }

    init {
       updateLiveDataFields()
    }

    fun processKeyPress(keyCode: Int)  {
        val mappingResult = KeyPressShotMapper.map(keyCode)
        mappingResult.shot?.play()
    }

    private fun Shot.play() {
        match.playShot(this)
        updateLiveDataFields()
    }

    private fun updateLiveDataFields() {
        match.apply {
            _shotTicker.value = currentFrame.shotTicker
            _playerOneScore.value = currentFrame.scoreFor(playerOne)
            _playerTwoScore.value = currentFrame.scoreFor(playerTwo)
            _playerOneFrameScore.value = playerOneFrameScore
            _playerTwoFrameScore.value = playerTwoFrameScore
            _finished.value = (winner != null)
            _currentPlayer.value = match.currentFrame.currentPlayer
        }
    }
}