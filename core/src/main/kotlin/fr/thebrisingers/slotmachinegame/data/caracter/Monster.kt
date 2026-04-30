package fr.thebrisingers.slotmachinegame.data.caracter

data class Monster(
    val id: Int,
    val category: Category,
    val faction: Faction,
    val attackSpeed: Int = category.attackSpeed + faction.attackSpeedModifier,
)
