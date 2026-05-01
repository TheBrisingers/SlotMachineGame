package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.thebrisingers.slotmachinegame.battle.BattleState
import fr.thebrisingers.slotmachinegame.battle.BattleUI
import fr.thebrisingers.slotmachinegame.data.spell.spellCollection
import fr.thebrisingers.slotmachinegame.machine.MachineState
import fr.thebrisingers.slotmachinegame.machine.MachineUI
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

class GameScreen : KtxScreen, InputAdapter() {
    private lateinit var machineState: MachineState
    private lateinit var battleState: BattleState

    private lateinit var gameRenderer: GameRenderer

    private lateinit var machineUI: MachineUI
    private lateinit var battleUI: BattleUI // les 3 boutons de sorts + bouton skip

    private lateinit var stage: Stage

    override fun show() {
        stage = Stage(ScreenViewport())

        battleState = BattleState()
        machineState = MachineState()

        gameRenderer = GameRenderer(stage, machineState, battleState)

        battleUI = BattleUI(
            stage = stage,
            spells = spellCollection.subList(0, 3),
            onCast = { spell -> battleState.castSpell(spell) },
            onSpinWheel = { },
            world = battleState
        )
        machineUI = MachineUI(
            stage = stage,
            onSpin = { machineState.spin() }
        )

        Gdx.input.inputProcessor = stage
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0.12f, 0.12f, 0.18f, 1f)
        gameRenderer.render(delta)
    }

    override fun resize(width: Int, height: Int) {
        gameRenderer.resize(width, height)
    }

    override fun dispose() {
        gameRenderer.dispose()
        stage.disposeSafely()
    }

    private var spaceHoldStartTime = 0L
    private val holdThresholdMs = 500L // seuil hold = 500ms

    override fun keyDown(keycode: Int): Boolean {
        if (keycode == Keys.SPACE) {
            spaceHoldStartTime = System.currentTimeMillis()
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if (keycode == Keys.SPACE) {
            val duration = System.currentTimeMillis() - spaceHoldStartTime
            if (duration < holdThresholdMs) {
                changeFocus()
            } else {
                applyAction()
            }
        }
        return true
    }

    val isPress: Boolean
        get() = Gdx.input.isKeyPressed(Keys.SPACE)

    private fun changeFocus() {

    }

    private fun applyAction() {

    }
}
