package fr.thebrisingers.slotmachinegame.data.caracter

enum class Faction(val attackSpeedModifier: Int, val healthModifier: Int, val attackDamageModifier: Int) {
    GOBLIN(-1, -1, 0),
    ZOMBIE(1, +1, 0),
    SKELETON(0,0,0),
}


