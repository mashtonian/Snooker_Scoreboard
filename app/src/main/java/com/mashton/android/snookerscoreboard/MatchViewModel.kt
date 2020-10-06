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

    init {
        _shotTicker.value = ""
        _playerOneScore.value = 0
        _playerTwoScore.value = 0
    }

    fun processKeyPress(keyCode: Int) :Boolean {
        val mappingResult = KeyPressShotMapper.map(keyCode)
        mappingResult.shot?.play()
        return mappingResult.handled
    }

    private fun Shot.play() {
        match.playShot(this)
        _shotTicker.value = match.currentFrame.shotTicker
        _playerOneScore.value = match.currentFrame.playerOneScore
        _playerTwoScore.value = match.currentFrame.playerTwoScore
    }

    val match = Match(playerOneName, playerTwoName)
}