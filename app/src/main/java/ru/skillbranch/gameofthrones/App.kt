package ru.skillbranch.gameofthrones

import android.app.Application
import android.content.Context
import androidx.room.Room
import ru.skillbranch.gameofthrones.data.database.AppDatabase

class App : Application() {
    companion object {
        private lateinit var instanse:App
        private var database:AppDatabase? = null

        fun applicationContext(): Context {
            return instanse.applicationContext
        }

        fun getDatabase(): AppDatabase {
            if (database==null) {
                database =
                    Room.databaseBuilder(instanse, AppDatabase::class.java, "game_of_thrones")
                        .build()
            }
            return database!!
        }
    }

    init {
        instanse = this
    }
}