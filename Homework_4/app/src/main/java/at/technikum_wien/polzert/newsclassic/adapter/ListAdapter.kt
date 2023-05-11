package at.technikum_wien.polzert.newsclassic.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.technikum_wien.polzert.newsclassic.data.NewsItem
import at.technikum_wien.polzert.newsclassic.R
import com.bumptech.glide.Glide

class ListAdapter(items: List<NewsItem> = listOf()) : RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {
    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var itemClickListener : ((NewsItem)->Unit)? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        private val newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        private val newsAuthor = itemView.findViewById<TextView>(R.id.newsAuthor)
        private val newsDate = itemView.findViewById<TextView>(R.id.newsDate)
        init {
            itemView.setOnClickListener { itemClickListener?.invoke(items[absoluteAdapterPosition]) }
        }
        fun bind(item: NewsItem, position: Int) {
            Glide.with(itemView.context).load(item.imageUrl).into(newsImage)
            newsTitle.text = item.title
            newsAuthor.text = item.author
            newsDate.text = item.publicationDate.toString()

            if (position == 0) {
                newsTitle.setTextColor(Color.RED) // Highlight the title of the most recent news item
            } else {
                newsTitle.setTextColor(Color.BLACK) // Reset to default for recycled views
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val layoutIdForListItem: Int = R.layout.list_item
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListItem, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
