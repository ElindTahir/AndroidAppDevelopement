package at.technikum_wien.polzert.newsclassic.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.data.NewsItem
import at.technikum_wien.polzert.newsclassic.data.download.ImageDownloader
import java.net.MalformedURLException
import java.net.URL
import java.text.DateFormat

class ListAdapter(items: List<NewsItem> = listOf(), var showImages : Boolean = false) : RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {
    companion object {
        private const val TYPE_TOP = 0
        private const val TYPE_OTHERS = 1
        private val LOG_TAG: String = ListAdapter::class.java.simpleName
    }
    var items = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var itemClickListener : ((NewsItem)->Unit)? = null

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
        private val titleTextView = itemView.findViewById<TextView>(R.id.tv_news_item_title)
        private val authorTextView = itemView.findViewById<TextView>(R.id.tv_news_item_author)
        private val publicationDateTextView = itemView.findViewById<TextView>(R.id.tv_news_item_publication_date)
        private val itemImageView = itemView.findViewById<ImageView>(R.id.iv_news_item_icon)
        private val itemProgressBar = itemView.findViewById<ProgressBar>(R.id.pb_news_item_icon)

        init {
            itemView.setOnClickListener { itemClickListener?.invoke(items[absoluteAdapterPosition]) }
        }

        fun bind(index: Int) {
            titleTextView.text = items[index].title
            authorTextView.text = items[index].author
            publicationDateTextView.text = items[index].publicationDate
            if (!showImages) {
                itemImageView.visibility = View.INVISIBLE
                itemProgressBar.visibility = View.INVISIBLE
            } else {
                try {
                    itemImageView.visibility = View.INVISIBLE
                    itemProgressBar.visibility = View.VISIBLE
                    val imageUrl = URL(items[index].imageUrl)
                    itemImageView.tag = imageUrl

                    ImageDownloader.downloadImage(imageUrl) { url, bitmap ->
                        itemImageView.post {
                            if (bitmap != null && itemImageView.tag == url) {
                                itemImageView.setImageBitmap(bitmap)
                                itemImageView.visibility = View.VISIBLE
                            }
                            itemProgressBar.visibility = View.INVISIBLE
                        }
                    }
                } catch (ex: MalformedURLException) {
                    Log.d(LOG_TAG, "Invalid Image URL.", ex)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val context = parent.context
        val layoutIdForListItem = if (viewType == TYPE_TOP)
                            R.layout.list_item_top;
                        else
                            R.layout.list_item;
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(layoutIdForListItem, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun reload(showImages: Boolean) {
        this.showImages = showImages
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_TOP else TYPE_OTHERS
    }
}