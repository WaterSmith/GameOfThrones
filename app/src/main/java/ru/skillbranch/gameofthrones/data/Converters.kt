package ru.skillbranch.gameofthrones.data

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import ru.skillbranch.gameofthrones.data.database.ListStringConverter
import ru.skillbranch.gameofthrones.data.local.entities.*
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

fun CharacterRes.toCharacter(houseId: String):Character{
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
        houseId = houseId
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
        houseId = houseId
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

fun CharacterFlatFull.toCharacterFull():CharacterFull {
    return CharacterFull(
        id = id,
        name = name,
        words = words,
        born = born,
        died = born,
        titles = titles,
        aliases = aliases,
        house = house, //rel
        father = if (fatherId.isNotEmpty()) (RelativeCharacter(fatherId, fatherName, fatherHouse)) else null,
        mother = if (motherId.isNotEmpty()) (RelativeCharacter(motherId, motherName, motherHouse)) else null
    )
}