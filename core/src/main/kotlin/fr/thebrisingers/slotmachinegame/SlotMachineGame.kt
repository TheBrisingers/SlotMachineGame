package fr.thebrisingers.slotmachinegame

import fr.thebrisingers.slotmachinegame.screens.FirstScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.async.KtxAsync

class SlotMachineGame : KtxGame<KtxScreen>() {
    override fun create() {
        KtxAsync.initiate()

        addScreen(FirstScreen())
        setScreen<FirstScreen>()
    }
}
