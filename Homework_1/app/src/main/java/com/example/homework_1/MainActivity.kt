package com.example.homework_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var firstNumber: EditText
    private lateinit var secondNumber: EditText
    private lateinit var result: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var seekBarNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    firstNumber = findViewById(R.id.firstNumber)
    secondNumber = findViewById(R.id.secondNumber)
    result = findViewById(R.id.resultView)
    seekBar = findViewById(R.id.seekBar)
    seekBarNumber = findViewById(R.id.seekbarNumber)

    val calcButton = findViewById<Button>(R.id.calcButton)
    val navButton = findViewById<Button>(R.id.navButton)

    calcButton.setOnClickListener {
        val numberOne = firstNumber.text.toString().toIntOrNull() ?: 0
        val numberTwo = secondNumber.text.toString().toIntOrNull() ?: 0

        val sumOfTwoNumbers = numberOne + numberTwo
        result.text = sumOfTwoNumbers.toString()
    }

    navButton.setOnClickListener {
        val numberOne = firstNumber.text.toString().toIntOrNull() ?: 0
        val numberTwo = secondNumber.text.toString().toIntOrNull() ?: 0

        val sumOfTwoNumbers = numberOne + numberTwo

        val secondActivityIntent = Intent(this, SecondActivity::class.java)
        secondActivityIntent.putExtra("SUM", sumOfTwoNumbers)
        startActivity(secondActivityIntent)
    }

    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            seekBarNumber.text = p1.toString()
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }

    })








    }
}