package com.mashton.android.snookerscoreboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MatchViewModel(playerOneName :String, playerTwoName :String) : ViewModel() {

    private val _shotTicker = MutableLiveData<String>()
    val shotTicker: LiveData<String>
        get() = _shotTicker

    private val _playerOneScore = MutableLiveData<Int>()
    val playerOneScore: LiveData<Int>
        get() = _playerOneScore

    private val _playerTwoScore = MutableLiveData<Int>()
    val playerTwoScore: LiveData<Int>
        get() = _playerTwoScore

    private val _playerOneFrameScore = MutableLiveData<Int>()
    val playerOneFrameScore: LiveData<Int>
        get() = _playerOneFrameScore

    private val _playerTwoFrameScore = MutableLiveData<Int>()
    val playerTwoFrameScore: LiveData<Int>
        get() = _playerTwoFrameScore

    init {
        _shotTicker.value = ""
        _playerOneScore.value = 0
        _playerTwoScore.value = 0
        _playerOneFrameScore.value = 0
        _playerTwoFrameScore.value = 0
    }

    fun processKeyPress(keyCode: Int) :Boolean {
        val mappingResult = KeyPressShotMapper.map(keyCode)
        mappingResult.shot?.play()
        return mappingResult.handled
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
        }
    }

    val match = Match(playerOneName, playerTwoName)
}