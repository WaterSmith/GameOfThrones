package ru.skillbranch.gameofthrones.data.database

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(", ")
    }

    @TypeConverter
    fun toList(data: String): List<String?>? {
        return data.split(", ")
    }
}