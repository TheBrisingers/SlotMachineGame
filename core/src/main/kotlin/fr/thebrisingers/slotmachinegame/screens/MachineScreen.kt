package fr.thebrisingers.slotmachinegame.screens

import fr.thebrisingers.slotmachinegame.data.WHEEL_SYMBOL_PROPORTION
import fr.thebrisingers.slotmachinegame.data.machine.Machine
import fr.thebrisingers.slotmachinegame.machine.MachineRenderer
import fr.thebrisingers.slotmachinegame.data.toRandomWheelValue
import fr.thebrisingers.slotmachinegame.machine.MachineState
import fr.thebrisingers.slotmachinegame.machine.MachineUI
import ktx.app.KtxScreen

class MachineScreen : KtxScreen {
    private val machine = Machine(
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue(),
        WHEEL_SYMBOL_PROPORTION.toRandomWheelValue()
    )
    private val state = MachineState(machine)
    private val renderer = MachineRenderer()

    private val ui = MachineUI(onSpin = {
        state.spin()
    })

    override fun render(delta: Float) {
        state.update(delta)
        renderer.render(state.counters, state.stateTime, state.isSpinning)
        ui.draw(delta)
    }

    override fun dispose() {
        renderer.dispose()
        ui.dispose()
    }
}
