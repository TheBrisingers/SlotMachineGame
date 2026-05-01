package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.math.Rectangle
import fr.thebrisingers.slotmachinegame.data.WHEEL_SYMBOL_PROPORTION
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import fr.thebrisingers.slotmachinegame.data.toRandomWheelValue

class MachineState(val updateIncomes: (List<Pair<Symbol, Int>>) -> Unit) {

    private val machine = Machine(
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue()
    )


    fun spin() {
        machine.shuffleWheels()
        updateIncomes(machine.getIncomes())
    }
}

