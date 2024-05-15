package at.technikum_wien.polzert.newsclassic.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import at.technikum_wien.polzert.newsclassic.data.db.ListConverter
import com.bumptech.glide.load.model.ByteArrayLoader.Converter
import java.io.Serializable
import java.util.*


@Entity(tableName = "NewsItem")
@TypeConverters(ListConverter::class)
data class NewsItem(
    @PrimaryKey
    var identifier: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "link")
    var link: String?,
    @ColumnInfo(name = "description")
    var description: String?,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String?,
    @ColumnInfo(name = "author")
    var author: String?,
    @ColumnInfo(name = "publicationDate")
    var publicationDate: String,
    @ColumnInfo(name = "keywords")
    var keywords: List<String>
) : Serializable
