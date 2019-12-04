package ru.skillbranch.gameofthrones.data.database

import androidx.room.*
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem


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

    @Query("SELECT * FROM characters LIMIT 1")
    abstract suspend fun getFirstEntity(): Character?

}