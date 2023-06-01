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
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.adapter.ListAdapter
import at.technikum_wien.polzert.newsclassic.settings.SettingsActivity
import at.technikum_wien.polzert.newsclassic.viewmodels.NewsListViewModel


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private val viewModel by viewModels<NewsListViewModel>()
    private var adapter : ListAdapter? = null

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

        viewModel.error.observe(this) {
            errorTextView.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.newsItems.observe(this) {
            adapter?.items = it
        }

        viewModel.busy.observe(this) {
        }

        val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.action_reload)
        item.isEnabled = viewModel.busy.value != true
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
                viewModel.reload()
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
            viewModel.reload()
        } else if (key == getString(R.string.settings_image_display_key)) {
            adapter?.reload(getImageDisplay())
        }
    }
}