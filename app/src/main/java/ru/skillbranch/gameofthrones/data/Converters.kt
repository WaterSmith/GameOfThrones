package ru.skillbranch.gameofthrones.data

import androidx.room.TypeConverter
import ru.skillbranch.gameofthrones.data.local.entities.Character
import ru.skillbranch.gameofthrones.data.local.entities.CharacterItem
import ru.skillbranch.gameofthrones.data.local.entities.House
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

fun CharacterRes.toCharacter(houseRes: HouseRes):Character{
    return Character(
        id = url.split("/").last(),
        name = name,
        gender = gender,
        culture = culture,
        born = born,
        died = died,
        titles = titles,
        aliases = aliases,
        father = father.split("/").lastOrNull()?:"", //rel
        mother = mother.split("/").lastOrNull()?:"", //rel
        spouse = spouse,
        houseId = houseRes.shortName()
    )
}

fun CharacterRes.toCharacter():Character{
    return Character(
        id = url.split("/").last(),
        name = name,
        gender = gender,
        culture = culture,
        born = born,
        died = died,
        titles = titles,
        aliases = aliases,
        father = father.split("/").lastOrNull()?:"", //rel
        mother = mother.split("/").lastOrNull()?:"", //rel
        spouse = spouse,
        houseId = ""
    )
}

fun Character.toCharacterItem(): CharacterItem {
    return CharacterItem(
        id = id,
        name = name,
        house = houseId,
        titles = titles,
        aliases = aliases
    )
}

fun HouseRes.toHouse():House {
    return House(
        id = shortName(),
        name = name,
        region = region,
        coatOfArms = coatOfArms,
        words = words,
        titles = titles,
        seats = seats,
        currentLord = currentLord,
        heir = heir,
        overlord = overlord,
        founded = founded,
        founder = founder,
        diedOut = diedOut,
        ancestralWeapons = ancestralWeapons
    )
}

fun HouseRes.shortName():String {
    var shortName = ""
    NeedHouses.values().forEach {
        if (it.fullName.equals(this.name)) shortName = it.shortName
    }
    return shortName
}