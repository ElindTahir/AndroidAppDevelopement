package at.technikum_wien.polzert.newsclassic.repo

import at.technikum_wien.polzert.newsclassic.data.db.NewsDao
import at.technikum_wien.polzert.newsclassic.data.download.NewsDownloader
import at.technikum_wien.polzert.newsclassic.data.parser.RssParser

class NewsItemRepo(private val newsItemDao: NewsDao, private val newsDownloader: NewsDownloader, private val rssParser: RssParser) {
    private val logger = "NewsItemRepo";

    val allNewsItems = newsItemDao.getAll()

    suspend fun refreshNewsItems(url: String) : Boolean {
        val newsItems = newsDownloader.load(url)
        if (newsItems != null) {
            newsItemDao.insert(newsItems)
            return true;
        }
        return false;
    }

    suspend fun deleteAll(){
        newsItemDao.deleteAll()
    }


}