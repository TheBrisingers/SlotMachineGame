package fr.thebrisingers.slotmachinegame.data

data class Spell(
    val cost: SpellCost,
    val name: String,
    val description: String,
    val spellEffect: List<SpellEffect>
)
