package com.mashton.android.snookerscoreboard.screens.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MatchViewModelFactory(private val playerOneName :String, private val playerTwoName :String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            return MatchViewModel(playerOneName, playerTwoName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}