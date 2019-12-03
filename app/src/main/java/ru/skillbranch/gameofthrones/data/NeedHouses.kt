package ru.skillbranch.gameofthrones.data

import ru.skillbranch.gameofthrones.R

enum class NeedHouses(val shortName:String, val fullName:String, val themeId:Int) {
    STARK("Stark", "House Stark of Winterfell", R.style.StarkTheme),
    LANNISTER("Lannister", "House Lannister of Casterly Rock", R.style.LannisterTheme),
    TARGARYEN("Targaryen", "House Targaryen of King's Landing", R.style.TargaryenTheme),
    BARATHEON("Baratheon", "House Greyjoy of Pyke", R.style.BaratheonTheme),
    GREYJOY("Greyjoy", "House Tyrell of Highgarden", R.style.GreyjoyTheme),
    MARTEL("Martel", "House Baratheon of Dragonstone", R.style.MartelTheme),
    TYRELL("Tyrell", "House Nymeros Martell of Sunspear", R.style.TyrelTheme);
}