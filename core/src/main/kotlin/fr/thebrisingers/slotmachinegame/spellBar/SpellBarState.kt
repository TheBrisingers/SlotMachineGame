package fr.thebrisingers.slotmachinegame.spellBar

import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.spellCollection

class SpellBarState {
    val spells: List<Spell> = spellCollection.subList(0, 3)


}
