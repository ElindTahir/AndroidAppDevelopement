package com.example.homework_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private var newsItems: List<NewsItem>,
    private val onItemClicked: (NewsItem) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = newsItems[position].title
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }

    fun updateData(newsItems: List<NewsItem>) {
        this.newsItems = newsItems
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, onItemClicked: (NewsItem) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)

        init {
            itemView.setOnClickListener {
                onItemClicked.invoke(newsItems[adapterPosition])
            }
        }
    }
}
