package fr.thebrisingers.slotmachinegame.machine

import fr.thebrisingers.slotmachinegame.data.WHEEL_SYMBOL_PROPORTION
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.data.spell.Symbol
import fr.thebrisingers.slotmachinegame.data.toRandomWheelValue

class MachineState(val onSpinResult: (List<Pair<Symbol, Int>>, Int, Int) -> Unit) {

    val machine = Machine(
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue()
    )


    fun spin() {
        machine.shuffleWheels()
        val incomes = machine.getIncomes()
        println(incomes)
        // On envoie les deux infos à GameScreen
        onSpinResult(incomes.runes, incomes.coins, incomes.heals)
    }
}

