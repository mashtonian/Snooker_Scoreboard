package com.mashton.android.snookerscoreboard

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import org.greenrobot.eventbus.EventBus

/**
 * Creates an Activity that hosts all of the fragments in the app
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        EventBus.getDefault().post(Event(keyCode))
        return super.onKeyDown(keyCode, event)
    }
}

data class Event(val keyCode :Int)