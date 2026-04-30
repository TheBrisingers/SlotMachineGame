package fr.thebrisingers.slotmachinegame.data

data class SpellEffect(
    val target: Target,
    val value: Int,
    val additionalEffect: String? = null,
)
