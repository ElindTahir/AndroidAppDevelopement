package at.technikum_wien.polzert.newsclassic.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.technikum_wien.polzert.newsclassic.NewsClassicApplication
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.adapter.ListAdapter
import at.technikum_wien.polzert.newsclassic.data.download.NewsDownloader
import at.technikum_wien.polzert.newsclassic.data.parser.RssParser
import at.technikum_wien.polzert.newsclassic.repo.NewsItemRepo
import at.technikum_wien.polzert.newsclassic.settings.SettingsActivity
import at.technikum_wien.polzert.newsclassic.viewmodels.NewsListViewModel
import at.technikum_wien.polzert.newsclassic.viewmodels.NewsListViewModelProviderFactory


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    //private val viewModel by viewModels<NewsListViewModel>()
    private var adapter : ListAdapter? = null

    private val newsListViewModel: NewsListViewModel by viewModels(
        factoryProducer =
        {
            NewsListViewModelProviderFactory(createNewsClassicRepository())
        }
    )

    private fun createNewsClassicRepository(): NewsItemRepo {
        return NewsItemRepo(
            (application as NewsClassicApplication).database.newsItemDao(),
            NewsDownloader(),
            RssParser()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_list)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        adapter = ListAdapter(showImages = getImageDisplay())
        recyclerView.adapter = adapter
        adapter?.itemClickListener = {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.ITEM_KEY, it)
            startActivity(intent)
        }

        val errorTextView = findViewById<TextView>(R.id.tv_error)

        newsListViewModel.error.observe(this) {
            errorTextView.visibility = if (it) View.VISIBLE else View.GONE
        }

        newsListViewModel.allNewsItems.observe(this) {
            adapter?.items = it
        }

        newsListViewModel.busy.observe(this) {
        }

        val sharedPreferences: SharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.action_reload)
        item.isEnabled = newsListViewModel.busy.value != true
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun getImageDisplay(): Boolean {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        return sharedPreferences.getBoolean(
            getString(R.string.settings_image_display_key),
            resources.getBoolean(R.bool.settings_image_display_default)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reload -> {
                newsListViewModel.refreshViewModel(this)
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String) {
        if (key == getString(R.string.settings_news_url_key)) {
            newsListViewModel.refreshViewModel(this)
        } else if (key == getString(R.string.settings_image_display_key)) {
            adapter?.reload(getImageDisplay())
        }
    }
}
