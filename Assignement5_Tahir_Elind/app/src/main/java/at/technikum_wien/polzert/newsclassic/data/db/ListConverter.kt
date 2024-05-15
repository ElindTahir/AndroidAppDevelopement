package at.technikum_wien.polzert.newsclassic.data.db

import androidx.room.TypeConverter

const val SEPARATOR = "____"

class ListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(SEPARATOR)
    }

    @TypeConverter
    fun stringListTo(input: List<String>): String {
        return input.joinToString(SEPARATOR)
    }
}

