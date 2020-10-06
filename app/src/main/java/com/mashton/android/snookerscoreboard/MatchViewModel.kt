package com.mashton.android.snookerscoreboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MatchViewModel(playerOneName :String, playerTwoName :String) : ViewModel() {

    val match = MutableLiveData<Match>()
    val shotTicker: LiveData<String> = Transformations.map(match) { it.currentFrame.shotTicker }

    init {
        match.value = Match(playerOneName, playerTwoName)
    }
}