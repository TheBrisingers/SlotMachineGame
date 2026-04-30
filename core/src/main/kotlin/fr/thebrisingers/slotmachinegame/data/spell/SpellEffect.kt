package fr.thebrisingers.slotmachinegame.data.spell

data class SpellEffect(
    val target: Target,
    val value: Int,
    val additionalEffect: String? = null,
)
