package com.example.homework_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    private lateinit var resultSecond: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        resultSecond = findViewById(R.id.resultView2)

        val resultFromMainActivity = intent.getIntExtra("SUM", 0)

        resultSecond.text = resultFromMainActivity.toString()
    }
}