package com.example.homework_3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class NewsListViewModel : ViewModel() {

    private val rssUrl = "https://www.engadget.com/"
    private val rssService: RssService

    var newsItems = MutableLiveData<List<NewsItem>>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(rssUrl)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        rssService = retrofit.create(RssService::class.java)
        fetchNewsItems()
    }

    fun fetchNewsItems() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = rssService.fetchRssFeed().execute()
            val rssFeed = response.body()

            if (rssFeed != null) {
                val sortedItems = rssFeed.channel.items.sortedByDescending { it.publicationDate }
                val mappedItems = sortedItems.map { NewsItem.fromRssItem(it) }
                newsItems.postValue(mappedItems)
            }
        }
    }

    fun refreshNewsItems() {
        fetchNewsItems()
    }


}