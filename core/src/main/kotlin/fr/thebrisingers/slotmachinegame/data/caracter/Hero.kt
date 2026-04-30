package fr.thebrisingers.slotmachinegame.data.caracter

import fr.thebrisingers.slotmachinegame.data.HERO_HEALTH

data class Hero(
    val id: Int,
    val health: Int = HERO_HEALTH,
)
