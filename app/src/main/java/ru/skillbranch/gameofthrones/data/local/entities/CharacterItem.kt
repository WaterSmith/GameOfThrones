package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.database.ListStringConverter

data class CharacterItem(
    val id: String,
    val house: String, //rel
    val name: String,
    @TypeConverters(ListStringConverter::class)
    val titles: List<String>,
    @TypeConverters(ListStringConverter::class)
    val aliases: List<String>
)

data class CharacterFull(
    val id: String,
    val name: String,
    val words: String,
    val born: String,
    val died: String,
    @TypeConverters(ListStringConverter::class)
    val titles: List<String>,
    @TypeConverters(ListStringConverter::class)
    val aliases: List<String>,
    val house:String, //rel
    val father: RelativeCharacter?,
    val mother: RelativeCharacter?
)

data class RelativeCharacter(
    val id: String,
    val name: String,
    val house:String //rel
)