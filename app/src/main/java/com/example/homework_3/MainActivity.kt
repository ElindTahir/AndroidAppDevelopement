package com.example.homework_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    //Todo: change unique identifier on details page


    private lateinit var adapter: RecyclerAdapter
    private lateinit var viewModel: NewsListViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RecyclerAdapter(emptyList()) { newsItem ->
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("newsItem", newsItem)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        //Initialize ViewModel
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        //Observe the newsItems LiveData
        viewModel.newsItems.observe(this, Observer { newsItems ->
            //update the adapter when newsItems change
            adapter.updateData(newsItems)
        })

        //SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchNewsItems()
            swipeRefreshLayout.isRefreshing = false
        }

        //Fetch news items initially
        viewModel.fetchNewsItems()













    }
}