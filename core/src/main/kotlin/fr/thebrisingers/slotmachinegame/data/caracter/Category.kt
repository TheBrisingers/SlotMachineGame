package fr.thebrisingers.slotmachinegame.data.caracter

enum class Category(val attackSpeed: Int, val health: Int, val attackDamage: Int, val baseReward: Int) {
    ARCHER(4, 10, -5, 2),   // Plus de PV, moins de dégâts
    ASSASSIN(3, 8, -4, 3),
    SOLDIER(5, 15, -8, 4),
}
