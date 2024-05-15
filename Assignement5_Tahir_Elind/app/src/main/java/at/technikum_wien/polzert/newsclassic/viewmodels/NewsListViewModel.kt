package at.technikum_wien.polzert.newsclassic.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import androidx.preference.PreferenceManager
import at.technikum_wien.polzert.newsclassic.R
import at.technikum_wien.polzert.newsclassic.data.download.NewsDownloader
import at.technikum_wien.polzert.newsclassic.data.NewsItem
import at.technikum_wien.polzert.newsclassic.repo.NewsItemRepo
import kotlinx.coroutines.launch

class NewsListViewModel(private val newsItemRepo: NewsItemRepo) : ViewModel() {

    private val _error = MutableLiveData(false)
    private val _busy = MutableLiveData(true)

    val allNewsItems = newsItemRepo.allNewsItems

    fun refreshViewModel(context: Context) {
        viewModelScope.launch {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val url =
                sharedPreferences.getString("news_url", "https://www.engadget.com/rss.xml")
                    ?: "https://www.engadget.com/rss.xml"

            val deletedItems = newsItemRepo.deleteAll()
            val fetchSuccessful = newsItemRepo.refreshNewsItems(url)
            _error.postValue(!fetchSuccessful)
        }
    }

    val error : LiveData<Boolean>
        get() = _error
    val busy : LiveData<Boolean>
        get() = _busy

}

class NewsListViewModelProviderFactory(private val newsItemRepo: NewsItemRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsListViewModel(newsItemRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
