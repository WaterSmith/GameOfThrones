package ru.skillbranch.gameofthrones.repositories

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

@RunWith(AndroidJUnit4::class)
class RootRepositoryTest {

    @Test
    fun get_all_houses() {
        //Запись персонажей
        val lock1 = Object()
        var actualHouses: List<HouseRes>? = null
        RootRepository.getAllHouses {
            actualHouses = it
            synchronized(lock1) { lock1.notify() }
        }
        synchronized(lock1) { lock1.wait() }

        val actualCharacters = actualHouses?.fold(mutableListOf<String>()) { acc, houses ->
            acc.also { it.addAll(houses.swornMembers) }
        }

        assertEquals(1567, actualCharacters?.size)
    }
}