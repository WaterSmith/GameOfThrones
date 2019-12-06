package ru.skillbranch.gameofthrones.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.NeedHouses
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.mutableLiveData
import ru.skillbranch.gameofthrones.repositories.MainRepository

class HousePageViewModel : ViewModel() {

    private val query = mutableLiveData("")
    private val house = mutableLiveData(NeedHouses.STARK)
    private val characters = mutableLiveData(listOf<CharacterItem>())

    fun setHouse(newHouse: NeedHouses) {
        house.value = newHouse
        MainRepository.findCharactersByHouseName(newHouse.shortName){
            characters.postValue(it)
        }

    }

    fun getHouse(): NeedHouses? {
        return house.value
    }

    fun getCharacterData(): LiveData<List<CharacterItem>> {
        val result = MediatorLiveData<List<CharacterItem>>()
        val filterF = {
            result.value =  if (query.value!!.isEmpty()) characters.value!!
            else characters.value!!.filter { it.name.contains(query.value!!, true) }
        }
        result.addSource(characters){filterF.invoke()}
        result.addSource(query){filterF.invoke()}

        return result
    }

}