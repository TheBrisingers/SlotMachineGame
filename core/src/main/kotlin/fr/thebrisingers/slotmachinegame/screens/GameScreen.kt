package fr.thebrisingers.slotmachinegame.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import fr.thebrisingers.slotmachinegame.FocusManager
import fr.thebrisingers.slotmachinegame.FocusTarget
import fr.thebrisingers.slotmachinegame.battle.BattleState
import fr.thebrisingers.slotmachinegame.inventory.InventoryState
import fr.thebrisingers.slotmachinegame.machine.MachineState
import fr.thebrisingers.slotmachinegame.machine.MachineUI
import fr.thebrisingers.slotmachinegame.spellBar.SpellBarState
import ktx.app.KtxScreen
import ktx.assets.disposeSafely

class GameScreen : KtxScreen, InputAdapter() {
    private lateinit var machineState: MachineState
    private lateinit var battleState: BattleState
    private lateinit var spellBarState: SpellBarState
    private lateinit var inventoryState: InventoryState

    private lateinit var gameRenderer: GameRenderer

    private lateinit var machineUI: MachineUI

    private lateinit var focusManager: FocusManager

    private lateinit var stage: Stage

    override fun show() {
        stage = Stage(ScreenViewport())

        battleState = BattleState()
        inventoryState = InventoryState()
        machineState = MachineState({ inventoryState.updateCounters(it) })
        spellBarState = SpellBarState()
        focusManager = FocusManager({ spellBarState.spells })

        gameRenderer = GameRenderer(stage, machineState, battleState, inventoryState)

        machineUI = MachineUI(
            stage = stage,
            onSpin = { machineState.spin() }
        )

        val multiplexer = InputMultiplexer()
        multiplexer.addProcessor(stage)
        multiplexer.addProcessor(this)
        Gdx.input.inputProcessor = multiplexer
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
        focusManager.next()
    }

    private fun applyAction() {
        when (val target = focusManager.current) {
            is FocusTarget.Spin -> {
                // TODO lancer l'animation de spin
                battleState.advanceMonsterTurn()
                machineState.spin()
            }
            is FocusTarget.Spell -> {
                val spell = spellBarState.spells[target.index]
                // TODO check si possible de lancer le sort
                if (true) {
                    battleState.castSpell(spell)
                } else {
                    // TODO faire une animation pour dire que pas lancable
                }
            }
        }
    }
}
