package com.example.homework_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        title = "Details Page"

        val newsItem = intent.getSerializableExtra("newsItem") as NewsItem

        val idTextView: TextView = findViewById(R.id.id_text_view)
        val titleTextView: TextView = findViewById(R.id.title_text_view)
        val descriptionTextView: TextView = findViewById(R.id.description_text_view)
        val imageUrlTextView: TextView = findViewById(R.id.imageUrl_text_view)
        val authorTextView: TextView = findViewById(R.id.author_text_view)
        val publicationDateTextView: TextView = findViewById(R.id.publicationDate_text_view)
        val fullArticleLinkTextView: TextView = findViewById(R.id.fullArticleLink_text_view)
        val keywordsTextView: TextView = findViewById(R.id.keywords_text_view)

        idTextView.text = newsItem.id.toString()
        titleTextView.text = newsItem.title
        descriptionTextView.text = Html.fromHtml(newsItem.description)
        imageUrlTextView.text = newsItem.imageUrl
        authorTextView.text = newsItem.author
        publicationDateTextView.text = newsItem.publicationDate
        fullArticleLinkTextView.text = newsItem.fullArticleLink
        keywordsTextView.text = newsItem.keywords.toString()
    }
}