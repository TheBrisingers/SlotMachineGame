package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.math.Rectangle
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import fr.thebrisingers.slotmachinegame.data.spell.Symbol

class MachineState(val machine: Machine) {
    val counters = listOf(
        SymbolRect(Symbol.FIRE.name, Rectangle(50f, 100f, 100f, 60f)),
        SymbolRect(Symbol.WATER.name, Rectangle(200f, 100f, 100f, 60f)),
        SymbolRect(Symbol.EARTH.name, Rectangle(350f, 100f, 100f, 60f)),
        SymbolRect(Symbol.WIND.name, Rectangle(500f, 100f, 100f, 60f))
    )

    fun spin() {
        machine.shuffleWheels()
        val incomes = machine.getIncomes()

        incomes.forEach { income ->
            income?.let { (symbol, gain) ->
                counters.find { it.title == symbol.name }?.let {
                    it.value += gain
                }
            }
        }

        println("${machine.spinResult} \nGains: $incomes")
    }
}

