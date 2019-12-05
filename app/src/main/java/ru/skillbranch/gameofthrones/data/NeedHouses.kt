package ru.skillbranch.gameofthrones.data

import ru.skillbranch.gameofthrones.R

enum class NeedHouses(val shortName:String, val fullName:String, val iconId:Int) {
    STARK("Stark", "House Stark of Winterfell", R.drawable.stark_icon),
    LANNISTER("Lannister", "House Lannister of Casterly Rock", R.drawable.lanister_icon),
    TARGARYEN("Targaryen", "House Targaryen of King's Landing", R.drawable.targaryen_icon),
    BARATHEON("Baratheon", "House Greyjoy of Pyke", R.drawable.baratheon_icon),
    GREYJOY("Greyjoy", "House Tyrell of Highgarden", R.drawable.greyjoy_icon),
    MARTEL("Martel", "House Baratheon of Dragonstone", R.drawable.martel_icon),
    TYRELL("Tyrell", "House Nymeros Martell of Sunspear", R.drawable.tyrel_icon);
}