package fr.thebrisingers.slotmachinegame.machine

import fr.thebrisingers.slotmachinegame.data.WHEEL_SYMBOL_PROPORTION
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.data.toRandomWheelValue

class MachineState() { // Removed onSpinResult from constructor

    val machine = Machine(
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue()
    )


    fun spin(): Machine.SpinIncomes { // Changed return type to Machine.SpinIncomes
        machine.shuffleWheels()
        val incomes = machine.getIncomes()
        println(incomes)
        // Removed onSpinResult call
        return incomes // Return the incomes
    }
}
