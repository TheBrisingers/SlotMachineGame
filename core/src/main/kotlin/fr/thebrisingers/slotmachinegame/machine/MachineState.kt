package fr.thebrisingers.slotmachinegame.machine

import com.badlogic.gdx.math.Rectangle
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.data.machine.SymbolRect
import fr.thebrisingers.slotmachinegame.data.spell.Symbol

class MachineState(val machine: Machine) {
    var stateTime = 0f
    var isSpinning = false
    private val spinDuration = 1.0f // L'animation dure 1 seconde
    private var timer = 0f
    val counters = listOf(
        SymbolRect(Symbol.FIRE.name, Rectangle(50f, 100f, 100f, 60f)),
        SymbolRect(Symbol.WATER.name, Rectangle(200f, 100f, 100f, 60f)),
        SymbolRect(Symbol.EARTH.name, Rectangle(350f, 100f, 100f, 60f)),
        SymbolRect(Symbol.WIND.name, Rectangle(500f, 100f, 100f, 60f))
    )

    fun spin() {
        if(isSpinning) return

        isSpinning = true
        timer = 0f
        stateTime = 0f

        machine.shuffleWheels()
    }

    fun update(delta: Float) {
        if (isSpinning) {
            stateTime += delta
            timer += delta

            if (timer >= spinDuration) {
                isSpinning = false
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
    }

}

