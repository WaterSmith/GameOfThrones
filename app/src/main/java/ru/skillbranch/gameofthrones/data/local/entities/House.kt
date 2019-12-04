package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.database.ListStringConverter
import java.util.*
import java.util.stream.Collectors


@Entity(tableName = "houses")
data class House(
    @PrimaryKey
    val id: String,
    val name: String,
    val region: String,
    val coatOfArms: String,
    val words: String,
    @TypeConverters(ListStringConverter::class)
    val titles: List<String>,
    @TypeConverters(ListStringConverter::class)
    val seats: List<String>,
    val currentLord: String, //rel
    val heir: String, //rel
    val overlord: String,
    val founded: String,
    val founder: String, //rel
    val diedOut: String,
    @TypeConverters(ListStringConverter::class)
    val ancestralWeapons: List<String>
)