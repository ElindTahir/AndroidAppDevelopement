package at.technikum_wien.polzert.newsclassic.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.data.download.NewsDownloader
import at.technikum_wien.polzert.newsclassic.data.NewsItem
import kotlinx.coroutines.launch

class NewsListViewModel(application : Application) : AndroidViewModel(application) {
    private val _newsItems = MutableLiveData<List<NewsItem>>(listOf())
    private val _error = MutableLiveData(false)
    private val _busy = MutableLiveData(true)

    init {
        reload()
    }

    val newsItems : LiveData<List<NewsItem>>
        get() = _newsItems
    val error : LiveData<Boolean>
        get() = _error
    val busy : LiveData<Boolean>
        get() = _busy

    private fun downloadNewsItems(newsFeedUrl: String) {
        _error.value = false
        _newsItems.value = listOf()
        _busy.value = true
        viewModelScope.launch {
            val value = NewsDownloader().load(newsFeedUrl)
            if (value == null)
                _error.value = true
            else
                _newsItems.value = value
            _busy.value = false
        }
    }

    private fun getUrl(): String {
        val context = getApplication<Application>().applicationContext
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString(
            context.getString(R.string.settings_news_url_key),
            context.getString(R.string.settings_news_url_default))
            ?: context.getString(R.string.settings_news_url_default)
    }

    fun reload() {
        downloadNewsItems(getUrl())
    }
}
