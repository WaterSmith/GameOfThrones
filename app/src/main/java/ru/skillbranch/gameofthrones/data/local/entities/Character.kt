package ru.skillbranch.gameofthrones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.database.ListStringConverter

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: String,
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    @TypeConverters(ListStringConverter::class)
    val titles: List<String> = listOf(),
    @TypeConverters(ListStringConverter::class)
    val aliases: List<String> = listOf(),
    val father: String, //rel
    val mother: String, //rel
    val spouse: String,
    val houseId: String//rel
)

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

data class CharacterFlatFull(
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
    val fatherId: String,
    val fatherName: String,
    val fatherHouse: String,
    val motherId: String,
    val motherName: String,
    val motherHouse: String
)