package fr.thebrisingers.slotmachinegame.data.caracter

data class Monster(
    val id: Int,
    val category: Category,
    val faction: Faction,
) : Entity {

    val maxHealth = category.health + faction.healthModifier
    var health: Int = maxHealth
        private set

    private val attackSpeed: Int = category.attackSpeed + faction.attackSpeedModifier

    var turnsUntilNextAttack = attackSpeed
        private set

    val attackThisTurn get() = turnsUntilNextAttack == 0

    val attackDamage: Int = category.attackDamage + faction.attackDamageModifier

    val isAlive get() = health > 0

    override fun takeDamage(damage: Int) {
        health = (health + damage).coerceIn(0..maxHealth)
    }

    fun advanceTurn() {
        if (turnsUntilNextAttack > 0) {
            turnsUntilNextAttack--
        }
    }

    fun resetTurnsUntilNextAttack() {
        turnsUntilNextAttack = attackSpeed
    }
}
