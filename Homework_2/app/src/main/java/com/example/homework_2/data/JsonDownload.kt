package com.example.homework_2.data


import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class DownloadJson {
    suspend fun downloadJsonData(page: Int = 1): List<Card> = withContext(Dispatchers.IO) {
        val url = URL("https://api.magicthegathering.io/v1/cards?page=$page")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        try {
            val inputStream = connection.inputStream
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val cardsJson = JSONObject(jsonString).getJSONArray("cards")
            val cards = mutableListOf<Card>()

            for (i in 0 until cardsJson.length()) {
                val cardJson = cardsJson.getJSONObject(i)
                val colorsJson = cardJson.optJSONArray("colors")
                val colors = if (colorsJson != null) {
                    List(colorsJson.length()) { colorsJson.getString(it) }
                } else {
                    emptyList()
                }

                val card = Card(
                    name = cardJson.getString("name"),
                    colors = colors
                )

                cards.add(card)
            }

            sortCardsAlphabetically(cards)

            return@withContext cards

        } catch (e: Exception) {
            Log.e("MainActivity", "Error downloading JSON data: ${e.message}")
            withContext(Dispatchers.Main) {
            }
            return@withContext emptyList<Card>()
        } finally {
            connection.disconnect()
        }
    }

    private fun sortCardsAlphabetically(cards: MutableList<Card>) {
        cards.sortWith(compareBy { it.name })
    }
}
