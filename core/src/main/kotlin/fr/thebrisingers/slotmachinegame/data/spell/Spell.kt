package fr.thebrisingers.slotmachinegame.data.spell

data class Spell(
    val cost: SpellCost,
    val name: String,
    val description: String,
    val spellEffect: List<SpellEffect>
)
