package com.example.homework_3

import java.io.Serializable

data class NewsItem(
    val title: String,
    val description: String,
    val imageUrl: String?,
    val author: String?,
    val publicationDate: String?,
    val fullArticleLink: String?,
    val keywords: List<String>,
    val id: Int
) : Serializable {
    companion object {
        fun fromRssItem(rssItem: RssItem): NewsItem {
            return NewsItem(
                title = rssItem.title,
                description = rssItem.description,
                imageUrl = rssItem.imageUrl,
                author = rssItem.author,
                publicationDate = rssItem.publicationDate,
                fullArticleLink = rssItem.fullArticleLink,
                keywords = rssItem.keywords?.split(",") ?: emptyList(),
                id = 1
            )
        }
    }
}