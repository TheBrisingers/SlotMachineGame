package fr.thebrisingers.slotmachinegame.inventory

import com.badlogic.gdx.math.Rectangle
import fr.thebrisingers.slotmachinegame.data.COMBAT_X
import fr.thebrisingers.slotmachinegame.data.INVENTORY_H
import fr.thebrisingers.slotmachinegame.data.INVENTORY_W
import fr.thebrisingers.slotmachinegame.data.INVENTORY_X
import fr.thebrisingers.slotmachinegame.data.INVENTORY_Y
import fr.thebrisingers.slotmachinegame.data.PANEL_X
import fr.thebrisingers.slotmachinegame.data.caracter.*
import fr.thebrisingers.slotmachinegame.data.gameStatus.BattleEvent
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import fr.thebrisingers.slotmachinegame.data.spell.Spell
import fr.thebrisingers.slotmachinegame.data.spell.SpellCost
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import fr.thebrisingers.slotmachinegame.data.spell.Target
import kotlin.collections.plusAssign

class InventoryState {
    private val iconSize = 32f
    private val gap = 6f

    // On calcule la hauteur totale occupée par les 4 rectangles et les 3 espaces
    private val totalGroupHeight = (iconSize * 4) + (gap * 3)


    // On calcule le point de départ Y pour que le groupe soit centré verticalement
    private val startY = INVENTORY_Y + (INVENTORY_H - totalGroupHeight) / 2f
    private val centerX = INVENTORY_X + (INVENTORY_W - iconSize) / 2f

    val counters = listOf(
        SymbolRect(Symbol.FIRE.name,  Rectangle(centerX, calculateY(3), iconSize, iconSize)),
        SymbolRect(Symbol.WATER.name, Rectangle(centerX, calculateY(2), iconSize, iconSize)),
        SymbolRect(Symbol.EARTH.name, Rectangle(centerX, calculateY(1), iconSize, iconSize)),
        SymbolRect(Symbol.WIND.name,  Rectangle(centerX, calculateY(0), iconSize, iconSize))
    )

    /**
     * Calcule la position Y en fonction de l'index avec un espacement serré
     */
    private fun calculateY(index: Int): Float {
        return startY + (index * (iconSize + gap))
    }

    fun updateCounters (incomes: List<Pair<Symbol, Int>>) {
        incomes.forEach { income ->
            income.let { (symbol, gain) ->
                counters.find { it.title == symbol.name }?.let {
                    it.value += gain
                }
            }
        }
    }

    fun canCastSpell(spellCost: SpellCost): Boolean {
        // .all vérifie que pour chaque symbole, on a assez de ressources
        return spellCost.spellCostMap.all { (symbol, requiredAmount) ->
            val currentAmount = counters.find { it.title == symbol.name }?.value ?: 0
            currentAmount >= requiredAmount
        }
    }

    fun consumeResources(spellCost: SpellCost) {
        spellCost.spellCostMap.forEach { (symbol, amount) ->
            if (amount > 0) {
                counters.find { it.title == symbol.name }?.let {
                    it.value -= amount
                }
            }
        }
    }
}
