package at.technikum_wien.polzert.newsclassic.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import at.technikum_wien.polzert.newsclassic.data.NewsItem

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsItem")
    fun getAll(): LiveData<List<NewsItem>>

    @Query("SELECT * FROM NewsItem WHERE title = :title")
    fun getById(title: String): LiveData<NewsItem?>

    @Query("DELETE FROM NewsItem")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsItems: List<NewsItem>) : List<Long>
}