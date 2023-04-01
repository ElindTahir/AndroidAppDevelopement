package com.example.homework_2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var loadCardsButton: Button
    private lateinit var CardView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCardsButton = findViewById(R.id.loadCardButton)
        CardView = findViewById(R.id.CardsView)

        loadCardsButton.setOnClickListener {
            CardView.text = "loading..."
            loadCardsButton.isEnabled = false

            //Loading Cards...

            loadCardsButton.isEnabled = true
        }













    }
}