package at.technikum_wien.polzert.newsclassic

import android.app.Application
import at.technikum_wien.polzert.newsclassic.data.db.ItemDatabase

class NewsClassicApplication : Application() {
    val database: ItemDatabase by lazy { ItemDatabase.getDatabase(this) }
}