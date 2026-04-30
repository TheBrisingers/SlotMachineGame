package fr.thebrisingers.slotmachinegame.data.caracter

enum class Faction(val attackSpeedModifier: Int, val healthModifier: Int, val damageModifier: Int) {
    GOBLIN(-1, -5, 0),
    ZOMBIE(1, +5, 0),
    SKELETON(0,0,0),
}


