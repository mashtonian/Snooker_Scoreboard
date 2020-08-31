package com.mashton.android.snookerscoreboard

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.stream.Collectors

class ShotList {
    var shots = ArrayList<Shot>()

    val score: Int
        @RequiresApi(Build.VERSION_CODES.N)
        get() = shots.stream().collect(Collectors.summingInt(Shot::value))

    fun add(shot: Shot)
    {
        shots.add(shot)
    }

    fun add(newShots: ArrayList<Shot>)
    {
        shots.addAll(newShots)
    }

}