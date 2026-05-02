package fr.thebrisingers.slotmachinegame.spellBar

import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.spellCollection

class SpellBarState(
) {
    val spells: List<Spell> = spellCollection.shuffled().subList(0, 5)

    var title = ""
    var description = ""

    fun updateDescription(target: FocusTarget) {
        when(target){
            is FocusTarget.Spell -> {
                val selectedSpell = spells[target.index]
                title = selectedSpell.name
                description = selectedSpell.description
            }
            is FocusTarget.Spin -> {
                title = "Table des Gains"
                description =
                    "Runes : x2 (+1) | x3 (+3)\n" +
                        "Soin  : x2 (10%) | x3 (25%)\n" +
                        "Pièces: x2 (1.5x) | x3 (3x)\n" +
                        "Triple pièces: x2 (3x) | x3 (5x)\n" +
                        "Sacs  : x2 (5x) | x3 (10x)\n" +
                        "Jokers: x3 (300 pièces)\n"
            }
        }
    }
}
