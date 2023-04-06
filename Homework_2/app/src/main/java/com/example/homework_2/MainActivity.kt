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
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var loadCardsButton: Button
    private lateinit var CardView: TextView

    private var currentPage = 1
    private var cards: ArrayList<Card>? = null

    //object to store two keys in order to save and restore the state of activity
    companion object {
        private const val _Cards = "cards"
        private const val _Page = "page"
    }

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadCardsButton = findViewById(R.id.loadCardButton)
        CardView = findViewById(R.id.CardsView)

        //If available restore state
        if(savedInstanceState != null) {
            val cards = savedInstanceState.getParcelableArrayList<Card>(_Cards)
            currentPage = savedInstanceState.getInt(_Page)
            showCards(cards)
        }

        loadCardsButton.setOnClickListener {
            CardView.text = "loading..."
            loadCardsButton.isEnabled = false

            //Loading Cards...

            CoroutineScope(Dispatchers.Main).launch {
                val cards = withContext(Dispatchers.IO) {
                    DownloadJson().downloadJsonData(currentPage)
                }

                if (cards.isEmpty()) {
                    currentPage = 1
                    val firstPageCards = withContext(Dispatchers.IO) {
                        DownloadJson().downloadJsonData(currentPage)
                    }
                    Log.e("LoadCardsEmpty", "page $currentPage")
                    showCards(firstPageCards.toMutableList() as ArrayList<Card>)
                } else {
                    Log.e("LoadCardsNotEmpty", "page $currentPage")
                    showCards(cards.toMutableList() as ArrayList<Card>)
                }

                currentPage++
                loadCardsButton.isEnabled = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(_Page, currentPage)
        outState.putParcelableArrayList(_Cards, cards)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentPage = savedInstanceState.getInt(_Page)
    }


        @SuppressLint("SetTextI18n")
        private fun showCards(cards: ArrayList<Card>?) {
            if (cards != null && cards.isNotEmpty()) {
                this.cards = cards
                val cardNames = cards.joinToString(separator = "\n") {
                    "${it.name} - ${it.colors.joinToString()}"
                }
                CardView.text = cardNames
            } else {
                CardView.text = "No cards found"
            }
        }
}

