package ru.skillbranch.gameofthrones.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.retrofit.NetworkService

object MainRepository {
    val scope = CoroutineScope(SupervisorJob())

    fun getNeedHouses(houseNames: List<String>, result : (houses : List<HouseRes>) -> Unit) {
        val needHousesList = mutableListOf<HouseRes>()

        scope.launch {
            for (houseName in houseNames) {
                NetworkService.retrofitApi.getHouseByName(houseName).also {
                    if (it.isSuccessful) {
                        val house = it.body()
                        if (house.isNullOrEmpty().not()) needHousesList.addAll(house!!)
                    }
                }
            }
        }.invokeOnCompletion { result(needHousesList) }
    }

    fun getNeedHouseWithCharacters(houseNames: List<String>, result : (houses : List<Pair<HouseRes, List<CharacterRes>>>) -> Unit) {
        val needHousesWithCharactersList = mutableListOf<Pair<HouseRes,List<CharacterRes>>>()
        getNeedHouses(houseNames) {
            scope.launch {
                it.forEach {
                    val houseCharactersList = mutableListOf<CharacterRes>()
                    it.swornMembers.forEach {
                        NetworkService.retrofitApi.getCharacterById(it.split("/").last()).also {
                            if (it.isSuccessful) {
                                val characterRes = it.body()
                                if (characterRes != null) {
                                    houseCharactersList.add(characterRes)
                                }
                            }
                        }
                    }
                    needHousesWithCharactersList.add(it to houseCharactersList)
                }
            }.invokeOnCompletion { result(needHousesWithCharactersList) }
        }
    }

    fun findCharactersByHouseName(name : String, result: (characters : List<CharacterItem>) -> Unit) {
        var characterItems = listOf<CharacterItem>()
        val characterDao = App.getDatabase().getCharacterDao()
        val scope = CoroutineScope(SupervisorJob())
        //scope.launch {
        //isNeedUpdate {
        //    if (it) {
        //        val houseDao = App.getDatabase().getHouseDao()
        //        getNeedHouseWithCharacters(NeedHouses.values().map {it.shortName}){
        //            it.forEach {
        //                val house = it.first.toHouse()
        //                houseDao.upsert(house)
        //                characterDao.upsert(it.second.map{it.toCharacter(house.id)})
        //            }
        //        }
        //    }
        //}}.invokeOnCompletion {
            scope.launch {
                characterItems = characterDao.getItemsByHouseId(name)
            }.invokeOnCompletion { result(characterItems) }
        //}
    }

    fun isNeedUpdate(result: (isNeed : Boolean) -> Unit){
        var isNeed = false
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            val firstCharacter = App.getDatabase().getCharacterDao().getFirstEntity()
            val firstHouse = App.getDatabase().getHouseDao().getFirstEntity()
            isNeed = (firstCharacter==null && firstHouse==null)
        }.invokeOnCompletion { result(isNeed) }
    }
}