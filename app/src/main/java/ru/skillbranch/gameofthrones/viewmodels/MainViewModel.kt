package ru.skillbranch.gameofthrones.viewmodels

import androidx.lifecycle.ViewModel
import ru.skillbranch.gameofthrones.mutableLiveData

class MainViewModel : ViewModel() {
    private val needUpdate = mutableLiveData(false)
    private val hasInternet = mutableLiveData(false)
    private val dataLoaded = mutableLiveData(false)


}