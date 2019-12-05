package ru.skillbranch.gameofthrones.data.database

import android.util.Log
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
        Log.d("addtodb","${entity}")
        val id = insert(entity)
        if (id == -1L) {
            update(entity)
        }
    }

    @Transaction
    open fun upsert(entityes: List<T>) {
        Log.d("addtodb","${entityes}")
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