package ru.skillbranch.gameofthrones.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.data.NeedHouses

class HousePageViewModel : ViewModel() {

    private val house = MutableLiveData<NeedHouses>()
    val text: LiveData<String> = Transformations.map(house) {
        "Hello world from house: ${it.shortName}"
    }

    val themeId: LiveData<Int> = Transformations.map(house) {
        it.themeId
    }

    fun setHouse(newHouse: NeedHouses) {
        house.value = newHouse
    }
}