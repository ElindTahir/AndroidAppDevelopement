package at.technikum_wien.polzert.newsclassic.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.text.Html
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.data.NewsItem
import at.technikum_wien.polzert.newsclassic.databinding.ActivityDetailsBinding
import androidx.core.text.HtmlCompat

class DetailsActivity : AppCompatActivity() {
    companion object {
        const val ITEM_KEY = "item"
    }

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val item = intent?.extras?.getSerializable(ITEM_KEY) as? NewsItem
        if (item != null) {
            // Load image using Glide
            Glide.with(this)
                .load(item.imageUrl)
                .into(binding.imageView)

            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT) }
            binding.tvAuthor.text = item.author
            binding.tvPublicationDate.text = item.publicationDate.toString()
            binding.tvKeywords.text = item.keywords.joinToString("\n")

            // Set up button to open the full article in a browser
            val btnOpenInBrowser = findViewById<Button>(R.id.btn_open_in_browser)
            btnOpenInBrowser.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.link)
                startActivity(i)
            }
        }
    }
}