package fr.thebrisingers.slotmachinegame.battle

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import fr.thebrisingers.slotmachinegame.data.gameStatus.TurnPhase
import fr.thebrisingers.slotmachinegame.data.spell.spellCollection
import ktx.app.KtxScreen

class BattleScreen : KtxScreen {

    private lateinit var world: BattleWorld
    private lateinit var renderer: BattleRenderer
    private lateinit var ui: BattleUI  // les 3 boutons de sorts + bouton skip

    override fun show() {
        world = BattleWorld()
        renderer = BattleRenderer(world)
        ui = BattleUI(
            spells = spellCollection.subList(0, 3),
            onCast = { spell -> world.castSpell(spell) },
            onSpinWheel = { },
            world = world
        )
        Gdx.input.inputProcessor = ui.stage
    }

    override fun render(delta: Float) {
        renderer.render(delta)

        // Quand l'animation de résolution est finie, on prévient le world
        if (world.phase == TurnPhase.RESOLUTION && renderer.isAnimationDone) {
            world.checkBattleStatus()
        }

        ui.stage.act(delta)
        ui.stage.draw()
    }

    override fun resize(width: Int, height: Int) = renderer.resize(width, height)
    override fun dispose() = renderer.dispose()

}
