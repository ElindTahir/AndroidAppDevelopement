package at.technikum_wien.polzert.newsclassic.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.adapter.ListAdapter
import at.technikum_wien.polzert.newsclassic.databinding.ActivityMainBinding
import at.technikum_wien.polzert.newsclassic.viewmodels.NewsListViewModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences
    private lateinit var urlChangeListener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel by viewModels<NewsListViewModel> { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val adapter = ListAdapter()
        binding.rvList.adapter = adapter
        adapter.itemClickListener = {
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.ITEM_KEY, it)
            startActivity(intent)
        }

        binding.btnReload.setOnClickListener {
            viewModel.reload()
        }

        viewModel.error.observe(this) {
            binding.tvError.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.newsItems.observe(this) {
            adapter.items = it
        }

        viewModel.busy.observe(this) {
            binding.btnReload.isEnabled = !it
        }

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        // Create a listener for URL preference changes
        urlChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == "NewsFeedUrl") {
                // Fetch new data when URL changes
                viewModel.reload()
            }
        }
        

        // Register the listener
        sharedPref.registerOnSharedPreferenceChangeListener(urlChangeListener)

    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the listener when the activity is destroyed
        sharedPref.unregisterOnSharedPreferenceChangeListener(urlChangeListener)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
