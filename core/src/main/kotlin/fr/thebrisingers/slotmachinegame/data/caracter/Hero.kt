package fr.thebrisingers.slotmachinegame.data.caracter

import fr.thebrisingers.slotmachinegame.data.HERO_MAX_HEALTH

data class Hero(
    val maxHealth: Int = HERO_MAX_HEALTH,
) : Entity {

    var health = maxHealth
        private set

    val isAlive get() = health > 0

    override fun takeDamage(damage: Int) {
        health = (health + damage).coerceIn(0..HERO_MAX_HEALTH)
    }

    fun heal(amount: Int) {
        if (amount <= 0) return
        // On soigne sans dépasser le maximum de PV
        health = (health + amount).coerceAtMost(maxHealth)
    }
}
