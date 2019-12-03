package ru.skillbranch.gameofthrones.repositories

import android.util.Log
import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.*
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.retrofit.NetworkService

object RootRepository {

    /**
     * Получение данных о всех домах
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getAllHouses(result : (houses : List<HouseRes>) -> Unit) {
        val fullList = mutableListOf<HouseRes>()
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            var pageNumber = 1
            while (true) {
                    val houseResponse = NetworkService.retrofitApi.getHousesByPage(pageNumber++)
                    if (houseResponse.isSuccessful) {
                        val listOfHouses = houseResponse.body()
                        if (listOfHouses.isNullOrEmpty()) break
                        fullList.addAll(listOfHouses)
                    }
            }

        }.invokeOnCompletion { result(fullList) }
    }
    /**
     * Получение данных о требуемых домах по их полным именам (например фильтрация всех домов)
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о домах
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouses(vararg houseNames: String, result : (houses : List<HouseRes>) -> Unit) {
        val needHousesList = mutableListOf<HouseRes>()
        val scope = CoroutineScope(SupervisorJob())
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

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(vararg houseNames: String, result : (houses : List<Pair<HouseRes, List<CharacterRes>>>) -> Unit) {
        val needHousesWithCharactersList = mutableListOf<Pair<HouseRes,List<CharacterRes>>>()
        val scope = CoroutineScope(SupervisorJob())
        getNeedHouses(houseNames = *houseNames){
            scope.launch {
                for (house in it) {
                    val houseCharactersList = mutableListOf<CharacterRes>()
                    for (member in house.swornMembers) {
                        val memberId = member.split("/").last()
                        NetworkService.retrofitApi.getCharacterById(memberId).also {
                            if (it.isSuccessful) {
                                val characterRes = it.body()
                                if (characterRes != null) {
                                    houseCharactersList.add(characterRes)
                                }
                            }
                        }
                    }
                    needHousesWithCharactersList.add(house to houseCharactersList)
                }
            }.invokeOnCompletion { result(needHousesWithCharactersList) }
        }
    }

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses : List<HouseRes>, complete: () -> Unit) {
        //TODO implement me
    }

    /**
     * Запись данных о пересонажах в DB
     * @param characters - Список персонажей (модель CharterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharters(characters : List<CharacterRes>, complete: () -> Unit) {
        //TODO implement me
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        //TODO implement me
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findChartersByHouseName(name : String, result: (characters : List<CharacterItem>) -> Unit) {
        //TODO implement me
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharterFullById(id : String, result: (character : CharacterFull) -> Unit) {
        //TODO implement me
    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
    fun isNeedUpdate(result: (isNeed : Boolean) -> Unit){
        //TODO implement me
    }

}