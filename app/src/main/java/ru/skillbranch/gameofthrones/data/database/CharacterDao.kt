package ru.skillbranch.gameofthrones.data.database

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFlatFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.RelativeCharacter


@Dao
abstract class CharacterDao : BaseDao<Character>() {
    @Query("SELECT * FROM characters")
    abstract suspend fun getAll(): List<Character?>

    @Query("SELECT * FROM characters WHERE id = :id")
    abstract suspend fun getById(id: String): Character?

    @Query("SELECT id, houseId as house, name, titles, aliases FROM characters WHERE id = :id")
    abstract suspend fun getItemById(id: String): CharacterItem?

    @Query("SELECT id, houseId as house, name, titles, aliases FROM characters WHERE houseId = :house")
    abstract suspend fun getItemsByHouseId(house: String): List<CharacterItem>

    @Query("SELECT id, houseId as house, name, titles, aliases FROM characters WHERE houseId = :house")
    abstract fun getLiveItemsByHouseId(house: String): MutableLiveData<List<CharacterItem>>

    @Query("SELECT char.id id, char.name name, houseRel.words words, char.born born, char.died died, char.titles titles, char.aliases aliases, char.houseId house, " +
            "fatherChar.id fatherId, fatherChar.name fatherName, fatherChar.houseId fatherHouse," +
            "motherChar.id motherId, motherChar.name motherName, motherChar.houseId motherHouse " +
            "FROM characters char, houses houseRel, characters fatherChar, characters motherChar " +
            "WHERE houseRel.id == char.houseId AND char.id == :id " +
            "AND fatherChar.id == char.father AND motherChar.id == char.mother")
    abstract suspend fun getFlatFullById(id: String): CharacterFlatFull?

    @Query("SELECT id, name, houseId as house FROM characters WHERE id = :id")
    abstract suspend fun getRelativeById(id: String): RelativeCharacter?

    @Query("SELECT * FROM characters LIMIT 1")
    abstract suspend fun getFirstEntity(): Character?

}