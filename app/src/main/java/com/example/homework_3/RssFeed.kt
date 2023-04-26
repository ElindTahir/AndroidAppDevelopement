package com.example.homework_3

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.text.SimpleDateFormat
import java.util.*

//class to represent the RSS feed structure

@Root(name = "rss", strict = false)
data class RssFeed @JvmOverloads constructor(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: RssChannel = RssChannel()
)

@Root(name = "channel", strict = false)
data class RssChannel @JvmOverloads constructor(

    @field:ElementList(entry = "item", inline = true)
    @param:ElementList(entry = "item", inline = true)
    val items: List<RssItem> = emptyList()
)

@Root(name = "item", strict = false)
data class RssItem @JvmOverloads constructor(

    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String = "",

    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String = "",

    @field:Element(name = "media:content", required = false)
    @param:Element(name = "media:content", required = false)
    val imageUrl: String? = null,

    @field:Element(name = "dc:creator", required = false)
    @param:Element(name = "dc:creator", required = false)
    val author: String? = null,

    @field:Element(name = "pubDate", required = false)
    @param:Element(name = "pubDate", required = false)
    val publicationDate: String? = null,

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false)
    val fullArticleLink: String? = null,

    @field:Element(name = "media:keywords", required = false)
    @param:Element(name = "media:keywords", required = false)
    val keywords: String? = null

)

fun parseDate(dateString: String?): Date? {
    return if (dateString != null) {
        val dateFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        dateFormat.parse(dateString)
    } else {
        null
    }
}