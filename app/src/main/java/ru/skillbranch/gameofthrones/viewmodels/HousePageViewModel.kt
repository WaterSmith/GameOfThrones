package ru.skillbranch.gameofthrones.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.NeedHouses
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.mutableLiveData

class HousePageViewModel : ViewModel() {

    private val query = mutableLiveData("")
    private val house = mutableLiveData(NeedHouses.STARK)
    private val characters = App.getDatabase().getCharacterDao().getLiveItemsByHouseId(house = house.value!!.shortName)

    fun setHouse(newHouse: NeedHouses) {
        house.value = newHouse
    }
    fun getHouse(): NeedHouses? {
        return house.value
    }

    fun getCharacterData(): LiveData<List<CharacterItem>> {
        return characters
    }
}