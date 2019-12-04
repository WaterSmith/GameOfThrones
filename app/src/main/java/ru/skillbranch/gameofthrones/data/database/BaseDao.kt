package ru.skillbranch.gameofthrones.data.database

import androidx.room.*


@Dao
abstract class BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entity: T):Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(entity: List<T>): List<Long>

    @Update
    abstract fun update(entity: T)

    @Update
    abstract fun update(entityes: List<T>)

    @Delete
    abstract fun delete(entity: T)

    @Transaction
    open fun upsert(entity: T) {
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun upsert(entityes: List<T>) {
        val insertResult = insert(entityes)
        val updateList: MutableList<T> = ArrayList()
        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(entityes[i])
            }
        }
        if (updateList.isNotEmpty()) {
            update(updateList)
        }
    }

}