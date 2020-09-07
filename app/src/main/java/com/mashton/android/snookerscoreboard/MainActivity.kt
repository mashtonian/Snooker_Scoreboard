package com.mashton.android.snookerscoreboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val redButton: Button = findViewById(R.id.buttonRed)
        redButton.setOnClickListener {processShotClick(LegalShot.RED)}

        val blackButton: Button = findViewById(R.id.buttonBlack)
        blackButton.setOnClickListener {processShotClick(LegalShot.BLACK)}

        val penaltyFiveButton: Button = findViewById(R.id.buttonPen5)
        penaltyFiveButton.setOnClickListener {processShotClick(IllegalShot.PENALTY_FIVE)}

        val dotButton: Button = findViewById(R.id.buttonDot)
        dotButton.setOnClickListener {processShotClick(LegalShot.DOT)}

        scoreTicker = findViewById(R.id.scoreTicker)

    }

    private val frame = Frame()

    private lateinit var scoreTicker: TextView

    private fun processShotClick(shot: Shot) {
        frame.playShot(shot)
        scoreTicker.text = frame.shotTicker
    }
}