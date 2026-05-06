package fr.thebrisingers.slotmachinegame.spellBar

import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.SpellTier
import fr.thebrisingers.slotmachinegame.data.spell.spellCollection

class SpellBarState(
) {
    val spells: List<Spell> = listOf(
        spellCollection.filter { it.tier == SpellTier.TIER_1 }.shuffled().take(3),
        spellCollection.filter { it.tier == SpellTier.TIER_2 }.shuffled().take(1),
        spellCollection.filter { it.tier == SpellTier.TIER_3 }.shuffled().take(1),
    ).flatten()
    var title = ""
    var description = ""

    var details = listOf<String>()

    fun updateDescription(target: FocusTarget) {
        when(target){
            is FocusTarget.Spell -> {
                val selectedSpell = spells[target.index]
                title = selectedSpell.name
                description = selectedSpell.description
                details = selectedSpell.spellEffect.map { it.toString() }
            }
            is FocusTarget.Spin -> {
                title = ""
                description = ""
                details = emptyList()
            }
        }
    }
}
