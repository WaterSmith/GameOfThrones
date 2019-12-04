package ru.skillbranch.gameofthrones.data.database

import androidx.room.*
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.House

@Dao
abstract class HouseDao : BaseDao<House>() {
    @Query("SELECT * FROM houses")
    abstract fun getAll(): List<House?>

    @Query("SELECT * FROM houses WHERE id = :id")
    abstract fun getById(id: String): House?

    @Query("SELECT * FROM houses LIMIT 1")
    abstract fun getFirstEntity(): House?
}