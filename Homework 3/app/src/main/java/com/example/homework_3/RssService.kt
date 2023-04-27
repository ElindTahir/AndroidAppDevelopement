package com.example.homework_3

//class to fetch RSS feed using Retrofit

import retrofit2.Call
import retrofit2.http.GET

interface RssService {

    @GET("rss.xml")
    fun fetchRssFeed(): Call<RssFeed>
}