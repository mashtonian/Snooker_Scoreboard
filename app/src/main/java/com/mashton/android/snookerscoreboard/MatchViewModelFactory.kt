package com.mashton.android.snookerscoreboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MatchViewModelFactory(private val playerOneName :String,
                            private val playerTwoName :String,
                            private val numberOfFrames :Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(playerOneName, playerTwoName, numberOfFrames) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}