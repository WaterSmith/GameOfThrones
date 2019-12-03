package ru.skillbranch.gameofthrones.extensions

import ru.skillbranch.gameofthrones.data.NeedHouses
import ru.skillbranch.gameofthrones.data.local.entities.Character
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

fun HouseRes.shortName():String {
    var shortName = ""
    NeedHouses.values().forEach {
        if (it.fullName.equals(this.name)) shortName = it.shortName
    }
    return shortName
}

