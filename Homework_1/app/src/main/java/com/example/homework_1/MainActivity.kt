package com.example.homework_1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var firstNumber: EditText
    private lateinit var secondNumber: EditText
    private lateinit var result: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    firstNumber = findViewById(R.id.firstNumber)
    secondNumber = findViewById(R.id.secondNumber)
    result = findViewById(R.id.resultView)

    val calcButton = findViewById<Button>(R.id.calcButton)

    calcButton.setOnClickListener {
        val numberOne = firstNumber.text.toString().toIntOrNull() ?: 0
        val numberTwo = secondNumber.text.toString().toIntOrNull() ?: 0

        val sum = numberOne + numberTwo
        result.text = sum.toString()
    }








    }
}