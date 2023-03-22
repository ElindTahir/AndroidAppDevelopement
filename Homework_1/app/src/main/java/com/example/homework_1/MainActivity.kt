package com.example.homework_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    var firstNumber = findViewById<TextInputEditText>(R.id.firstNumber)
    var secondNumber = findViewById<TextInputEditText>(R.id.secondNumber)
    var resultView = findViewById<TextView>(R.id.resultView)

}