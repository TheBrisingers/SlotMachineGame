package fr.thebrisingers.slotmachinegame.data.caracter

enum class Category(val attackSpeed: Int, val health: Int, val attackDamage: Int, val baseReward: Int) {
    ARCHER(5, 3, -10, 2),
    ASSASSIN(4, 3, -7, 3),
    SOLDIER(6, 3, -13, 4),
}
