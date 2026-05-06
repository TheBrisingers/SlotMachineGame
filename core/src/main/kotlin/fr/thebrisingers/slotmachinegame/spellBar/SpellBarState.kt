package fr.thebrisingers.slotmachinegame.spellBar

import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.data.*
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
                    "Runes : x2 (+${FIRE_SYMBOL_EARNING.twoSymbolWin}) | x3 (+${FIRE_SYMBOL_EARNING.threeSymbolWin})\n" +
                        "Soin  : x2 (+${HEAL_SYMBOL_EARNING.twoSymbolWin}) | x3 (+${HEAL_SYMBOL_EARNING.threeSymbolWin})\n" +
                        "Pièces: x2 (+${SIMPLE_COIN_SYMBOL_EARNING.twoSymbolWin}) | x3 (+${SIMPLE_COIN_SYMBOL_EARNING.threeSymbolWin})\n" +
                        "Triple pièces: x2 (+${MULTIPLE_COIN_SYMBOL_EARNING.twoSymbolWin}) | x3 (+${MULTIPLE_COIN_SYMBOL_EARNING.threeSymbolWin})\n" +
                        "Sacs  : x2 (+${COIN_BAG_SYMBOL_EARNING.twoSymbolWin}) | x3 (+${COIN_BAG_SYMBOL_EARNING.threeSymbolWin})\n" +
                        "Jokers: x3 (+${JOKER_SYMBOL_EARNING.threeSymbolWin})\n"
            }
        }
    }
}
