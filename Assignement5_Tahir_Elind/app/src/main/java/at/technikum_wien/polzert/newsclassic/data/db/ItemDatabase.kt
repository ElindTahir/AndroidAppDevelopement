package at.technikum_wien.polzert.newsclassic.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import at.technikum_wien.polzert.newsclassic.data.NewsItem

@Database(entities = [NewsItem::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun newsItemDao(): NewsDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val tempInstance = INSTANCE
                if (tempInstance != null)
                    return tempInstance
                else {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ItemDatabase::class.java,
                        "news_database"
                    )   .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }
}