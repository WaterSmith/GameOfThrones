package ru.skillbranch.gameofthrones.repositories

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.skillbranch.gameofthrones.App
import ru.skillbranch.gameofthrones.data.local.entities.CharacterFull
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes
import ru.skillbranch.gameofthrones.data.toCharacter
import ru.skillbranch.gameofthrones.data.toCharacterFull
import ru.skillbranch.gameofthrones.data.toHouse
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
        MainRepository.getNeedHouses(houseNames = *houseNames,result = result)
    }

    /**
     * Получение данных о требуемых домах по их полным именам и персонажах в каждом из домов
     * @param houseNames - массив полных названий домов (смотри AppConfig)
     * @param result - колбек содержащий в себе список данных о доме и персонажей в нем (Дом - Список Персонажей в нем)
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNeedHouseWithCharacters(vararg houseNames: String, result : (houses : List<Pair<HouseRes, List<CharacterRes>>>) -> Unit) {
        MainRepository.getNeedHouseWithCharacters(houseNames = *houseNames, result = result)
    }

    /**
     * Запись данных о домах в DB
     * @param houses - Список персонажей (модель HouseRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertHouses(houses : List<HouseRes>, complete: () -> Unit) {
        val houseDao = App.getDatabase().getHouseDao()
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            houseDao.upsert(houses.map { it.toHouse() })
        }.invokeOnCompletion { complete() }
    }

    /**
     * Запись данных о пересонажах в DB
     * @param characters - Список персонажей (модель CharterRes - модель ответа из сети)
     * необходимо произвести трансформацию данных
     * @param complete - колбек о завершении вставки записей db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun insertCharacters(characters : List<CharacterRes>, complete: () -> Unit) {
        val characterDao = App.getDatabase().getCharacterDao()
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            characterDao.upsert(characters.map { it.toCharacter() })
        }.invokeOnCompletion { complete() }
    }

    /**
     * При вызове данного метода необходимо выполнить удаление всех записей в db
     * @param complete - колбек о завершении очистки db
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun dropDb(complete: () -> Unit) {
        val scope = CoroutineScope(SupervisorJob())
        scope.launch {
            App.getDatabase().clearAllTables()
        }.invokeOnCompletion { complete() }
    }

    /**
     * Поиск всех персонажей по имени дома, должен вернуть список краткой информации о персонажах
     * дома - смотри модель CharterItem
     * @param name - краткое имя дома (его первычный ключ)
     * @param result - колбек содержащий в себе список краткой информации о персонажах дома
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharactersByHouseName(name : String, result: (characters : List<CharacterItem>) -> Unit) {
        val characterDao = App.getDatabase().getCharacterDao()
        val scope = CoroutineScope(SupervisorJob())
        var characterItems = listOf<CharacterItem>()
        scope.launch {
            characterItems = characterDao.getItemsByHouseId(name)
        }.invokeOnCompletion {
            result(characterItems)
        }
    }

    /**
     * Поиск персонажа по его идентификатору, должен вернуть полную информацию о персонаже
     * и его родственных отношения - смотри модель CharterFull
     * @param id - идентификатор персонажа
     * @param result - колбек содержащий в себе полную информацию о персонаже
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun findCharacterFullById(id : String, result: (character : CharacterFull) -> Unit) {
        val characterDao = App.getDatabase().getCharacterDao()
        val scope = CoroutineScope(SupervisorJob())
        var characterFull : CharacterFull? = null
        scope.launch {
            characterFull = characterDao.getFlatFullById(id)?.toCharacterFull()
        }.invokeOnCompletion {
            result(characterFull!!)
        }    }

    /**
     * Метод возвращет true если в базе нет ни одной записи, иначе false
     * @param result - колбек о завершении очистки db
     */
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