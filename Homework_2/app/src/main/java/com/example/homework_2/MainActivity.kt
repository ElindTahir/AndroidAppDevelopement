package com.example.homework_2


import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.homework_2.data.Card
import com.example.homework_2.data.DownloadJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

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

            CoroutineScope(Dispatchers.Main).launch {
                val cards = withContext(Dispatchers.IO) {
                    DownloadJson().downloadJsonData(1)
                }
                CardView.text = cards.firstOrNull()?.name ?: "No cards found."
            }



            loadCardsButton.isEnabled = true
        }
    }
}

