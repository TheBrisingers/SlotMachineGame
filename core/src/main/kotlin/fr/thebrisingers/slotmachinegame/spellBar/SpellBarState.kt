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
                title = spellCollection[target.index].name
                description = spellCollection[target.index].description
            }
            is FocusTarget.Spin -> {
                title = "Les lots :"
                description = "2 à la suite = 1\n3 à la suite = 3"
            }
        }
    }
}
