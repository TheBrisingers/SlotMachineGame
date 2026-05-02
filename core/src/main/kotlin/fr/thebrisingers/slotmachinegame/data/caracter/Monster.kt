package fr.thebrisingers.slotmachinegame.data.caracter

data class Monster(
    val id: Int,
    val category: Category,
    val faction: Faction,
    val difficultyMultiplier: Float = 1f
) : Entity {

    val maxHealth = ((category.health + faction.healthModifier) * difficultyMultiplier).toInt()
    var health: Int = maxHealth
        private set

    val coinReward: Int = (category.baseReward * difficultyMultiplier).toInt().coerceAtLeast(1)

    private val attackSpeed: Int = category.attackSpeed + faction.attackSpeedModifier

    var turnsUntilNextAttack = attackSpeed
        private set

    val attackThisTurn get() = turnsUntilNextAttack == 1

    val attackDamage: Int = category.attackDamage + faction.attackDamageModifier

    val isAlive get() = health > 0

    override fun takeDamage(damage: Int) {
        health = (health + damage).coerceIn(0..maxHealth)
    }

    fun advanceTurn(): Boolean {
        val willAttack = attackThisTurn
        turnsUntilNextAttack--
        if (turnsUntilNextAttack <= 0) {
           resetTurnsUntilNextAttack() // Reset du compteur
        }
        return willAttack
    }

    fun resetTurnsUntilNextAttack() {
        turnsUntilNextAttack = attackSpeed
    }
}
